package com.wayne.taiwan_s_environment.view.home

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.MyApplication
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.api.OpenDataService
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.db.vo.UV
import com.wayne.taiwan_s_environment.model.exception.CountyNotFoundException
import com.wayne.taiwan_s_environment.model.pref.Pref
import com.wayne.taiwan_s_environment.utils.toDbUVList
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber
import java.util.*

class HomeViewModel : BaseViewModel() {

    private val openDateService: OpenDataService by inject()
    private val uvDao: UVDao by inject()
    private val pref: Pref by inject()

    private val _openUvUpdate = MutableLiveData<ApiResult<Nothing>>()
    val openUvUpdate: LiveData<ApiResult<Nothing>>
        get() = _openUvUpdate

    val county = MutableLiveData<String>().also { it.value = pref.county }

    private val _uvList = MutableLiveData<ApiResult<List<UV>>>()
    val uvList: LiveData<ApiResult<List<UV>>>
        get() = _uvList

    fun getUVOpenData() {
        viewModelScope.launch {
            flow {
                val result = openDateService.getUV()
                val body = result.body()
                if (!result.isSuccessful || body == null) throw HttpException(result)
                uvDao.insertUVAll(body.toDbUVList())
                emit(ApiResult.success(null))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _openUvUpdate.value = it }
        }
    }

    fun getUVByCounty(county: String) {
        viewModelScope.launch {
            flow {
                val uvList = uvDao.getAllByCounty(county)

                emit(ApiResult.success(uvList))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _uvList.value = it }
        }
    }

    fun getUVByLocation(location: Location) {
        viewModelScope.launch {
            flow {
                val geocoder = Geocoder(MyApplication.applicationContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val firstAddress = address.first()
                Timber.e("firstAddress : $firstAddress")
                val admin = firstAddress.adminArea
                Timber.e("admin : $admin")
                val uvList = uvDao.getAllByCounty(admin)
                if (uvList.isNullOrEmpty()) throw CountyNotFoundException()

                pref.county = admin
                county.postValue(admin)
                emit(ApiResult.success(uvList))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _uvList.value = it }
        }
    }
}