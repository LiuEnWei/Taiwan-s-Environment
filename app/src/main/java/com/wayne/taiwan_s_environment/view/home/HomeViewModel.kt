package com.wayne.taiwan_s_environment.view.home

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.MyApplication
import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.model.exception.CountyNotFoundException
import com.wayne.taiwan_s_environment.model.exception.SameCountyException
import com.wayne.taiwan_s_environment.utils.toDbAQIList
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

    private val repository: Repository by inject()

    private val _epaDataUpdate = MutableLiveData<ApiResult<Nothing>>()
    val epaDataUpdate: LiveData<ApiResult<Nothing>>
        get() = _epaDataUpdate

    val county = MutableLiveData<String>()

    private val _epaList = MutableLiveData<ApiResult<ArrayList<Home>>>()
    val epaList: LiveData<ApiResult<ArrayList<Home>>>
        get() = _epaList

    fun initData() {
        repository.getPrefCounty()?.let {
            Timber.e("initData $it")
            getUVByCounty(it)
        }
    }

    fun updateEpaData() {
        viewModelScope.launch {
            flow {
                val uvResult = repository.getUV()
                val uvRecords = uvResult.body()?.records
                if (!uvResult.isSuccessful || uvRecords == null) throw HttpException(uvResult)
                repository.insertUV(uvRecords.toDbUVList())

                val aqiResult = repository.getAQI()
                val aqiRecords = aqiResult.body()?.records
                if (!aqiResult.isSuccessful || aqiRecords == null) throw HttpException(aqiResult)
                repository.insertAQI(aqiRecords.toDbAQIList())

                emit(ApiResult.success(null))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect {
                    _epaDataUpdate.value = it
                    county.value?.let { county ->
                        getUVByCounty(county)
                    }
                }
        }
    }

    fun getUVByCounty(county: String) {
        viewModelScope.launch {
            flow {
                val list = repository.getAllByCounty(county)
                repository.setPrefCounty(county)
                if (list.isNotEmpty()) {
                    this@HomeViewModel.county.postValue(county)
                }
                emit(ApiResult.success(list))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _epaList.postValue(it) }
        }
    }

    fun getUVByLocation(location: Location) {
        viewModelScope.launch {
            flow {
                val geocoder = Geocoder(MyApplication.applicationContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val firstAddress = address.first()
                Timber.e("firstAddress : ${firstAddress.getAddressLine(0)}")
                val admin = firstAddress.adminArea
                Timber.e("admin : $admin")
                if (admin == county.value) throw SameCountyException()

                val list = repository.getAllByCounty(admin)
                if (list.isNullOrEmpty()) throw CountyNotFoundException()
                repository.setPrefCounty(admin)
                county.postValue(admin)
                emit(ApiResult.success(list))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _epaList.postValue(it) }
        }
    }

    fun isPowerSaving(): Boolean {
        return repository.isPowerSaving()
    }

    fun setPowerSaving(isPowerSaving: Boolean) {
        repository.setPowerSaving(isPowerSaving)
    }
}