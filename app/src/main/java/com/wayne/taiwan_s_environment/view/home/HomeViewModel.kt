package com.wayne.taiwan_s_environment.view.home

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.MyApplication
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.api.EpaDataService
import com.wayne.taiwan_s_environment.model.db.dao.AQIDao
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.model.exception.CountyNotFoundException
import com.wayne.taiwan_s_environment.model.exception.SameCountyException
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

    private val epaDateService: EpaDataService by inject()
    private val uvDao: UVDao by inject()
    private val aqiDao: AQIDao by inject()
    private val pref: Pref by inject()

    private val _epaDataUpdate = MutableLiveData<ApiResult<Nothing>>()
    val epaDataUpdate: LiveData<ApiResult<Nothing>>
        get() = _epaDataUpdate

    val county = MutableLiveData<String>()

    private val _epaList = MutableLiveData<ApiResult<ArrayList<Home>>>()
    val epaList: LiveData<ApiResult<ArrayList<Home>>>
        get() = _epaList

    fun initData() {
        Timber.e("initData ${pref.county}")
        pref.county?.let {
            getUVByCounty(it)
        }
    }

    fun getUVOpenData() {
        viewModelScope.launch {
            flow {
                val result = epaDateService.getUV()
                val records = result.body()?.records
                if (!result.isSuccessful || records == null) throw HttpException(result)
                uvDao.insertAll(records.toDbUVList())
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
        county.checkSiteCounty().let {
            viewModelScope.launch {
                flow {
                    val list = arrayListOf<Home>()
                    list.addAll(uvDao.getAllByCounty(it))
                    list.addAll(aqiDao.getAllByCounty(it))
                    list.sort()
                    pref.county = it
                    if (list.isNotEmpty()) {
                        this@HomeViewModel.county.postValue(it)
                    }
                    emit(ApiResult.success(list))
                }.flowOn(Dispatchers.IO)
                    .catch { e -> emit(ApiResult.error(e)) }
                    .collect { _epaList.value = it }
            }
        }
    }

    fun getUVByLocation(location: Location) {
        viewModelScope.launch {
            flow {
                val geocoder = Geocoder(MyApplication.applicationContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val firstAddress = address.first()
                Timber.e("firstAddress : ${firstAddress.getAddressLine(0)}")
                val admin = firstAddress.adminArea.checkSiteCounty()
                Timber.e("admin : $admin")
                if (admin == county.value) throw SameCountyException()

                val list = arrayListOf<Home>()
                list.addAll(uvDao.getAllByCounty(admin))
                list.addAll(aqiDao.getAllByCounty(admin))
                list.sort()
                if (list.isNullOrEmpty()) throw CountyNotFoundException()

                pref.county = admin
                county.postValue(admin)
                emit(ApiResult.success(list))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _epaList.value = it }
        }
    }

    fun isPowerSaving(): Boolean {
        return pref.isPowerSaving
    }

    fun setPowerSaving(isPowerSaving: Boolean) {
        pref.isPowerSaving = isPowerSaving
    }

    /**
     * 新竹市沒有UV觀測站
     * */
    private fun String.checkSiteCounty(): String {
        val hsinchuCity = context.resources.getString(R.string.hsinchu_city)
        if (this == hsinchuCity) {
            return context.resources.getString(R.string.hsinchu_county)
        } else {
            return this
        }
    }
}