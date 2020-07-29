package com.wayne.taiwan_s_environment.view.home

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.MyApplication
import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.model.Result
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.model.exception.CountyNotFoundException
import com.wayne.taiwan_s_environment.utils.toDbAQIList
import com.wayne.taiwan_s_environment.utils.toDbUVList
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.inject
import timber.log.Timber
import java.util.*

class HomeViewModel : BaseViewModel() {

    private val repository: Repository by inject()

    private val _epaDataUpdate = MutableLiveData<Result<Nothing>>()
    val epaDataUpdate: LiveData<Result<Nothing>>
        get() = _epaDataUpdate

    val county = MutableLiveData<String>()

    private val _epaList = MutableLiveData<Result<ArrayList<Home>>>()
    val epaList: LiveData<Result<ArrayList<Home>>>
        get() = _epaList

    fun initData() {
        repository.getPrefCounty()?.let {
            Timber.e("initData $it")
            getUVByCounty(it)
        }
    }

    fun updateEpaData() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
            val uvResult = repository.getUV()
            if (uvResult is Result.Success) {
                repository.insertUV(uvResult.result.toDbUVList())
            }
            val aqiResult = repository.getAQI()
            if (aqiResult is Result.Success) {
                repository.insertAQI(aqiResult.result.toDbAQIList())
            }

            when {
                uvResult is Result.Error -> {
                    _epaDataUpdate.value = Result.error(uvResult.throwable)
                }
                aqiResult is Result.Error -> {
                    _epaDataUpdate.value = Result.error(aqiResult.throwable)
                }
                else -> {
                    _epaDataUpdate.value = Result.success(null)
                }
            }

            county.value?.let { county ->
                getUVByCounty(county)
            }
        }
    }

    fun getUVByCounty(county: String) {
        viewModelScope.launch {
            _epaList.postValue(Result.loading())
            try {
                val list = repository.getAllByCounty(county)
                repository.setPrefCounty(county)
                if (list.isNotEmpty()) {
                    this@HomeViewModel.county.postValue(county)
                }
                _epaList.postValue(Result.success(list))
            } catch (e: Exception) {
                _epaList.postValue(Result.error(e))
            }
        }
    }

    fun getUVByLocation(location: Location) {
        viewModelScope.launch {
            _epaList.postValue(Result.loading())
            try {
                val geocoder = Geocoder(MyApplication.applicationContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val firstAddress = address.first()
                Timber.e("firstAddress : ${firstAddress.getAddressLine(0)}")
                val admin = firstAddress.adminArea.replaceAdmin()
                Timber.e("admin : $admin")
                val list = repository.getAllByCounty(admin)
                if (list.isNullOrEmpty()) throw CountyNotFoundException()
                repository.setPrefCounty(admin)
                county.postValue(admin)
                _epaList.postValue(Result.success(list))
            } catch (e: Exception) {
                _epaList.postValue(Result.error(e))
            }
        }
    }

    fun isPowerSaving(): Boolean {
        return repository.isPowerSaving()
    }

    fun setPowerSaving(isPowerSaving: Boolean) {
        repository.setPowerSaving(isPowerSaving)
    }

    fun String.replaceAdmin(): String {
        return this.replace("台", "臺")
    }
}