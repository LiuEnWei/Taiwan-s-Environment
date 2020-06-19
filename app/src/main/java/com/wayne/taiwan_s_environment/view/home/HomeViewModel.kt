package com.wayne.taiwan_s_environment.view.home

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.MyApplication
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.api.OpenDataService
import com.wayne.taiwan_s_environment.model.api.vo.UV
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber
import java.util.*

class HomeViewModel : BaseViewModel() {

    private val openDateService: OpenDataService by inject()

    private val _uvList = MutableLiveData<ApiResult<List<UV>>>()
    val uvList: LiveData<ApiResult<List<UV>>>
        get() = _uvList

    private val _address = MutableLiveData<ApiResult<String>>()
    val address: LiveData<ApiResult<String>>
        get() = _address

    fun getUV() {
        viewModelScope.launch {
            flow {
                val result = openDateService.getUV()
                if (!result.isSuccessful) throw HttpException(result)
                emit(ApiResult.success(result.body()))
            }.flowOn(Dispatchers.IO)
                .onStart {  }
                .catch { e -> emit(ApiResult.error(e)) }
                .onCompletion {  }
                .collect { _uvList.value = it }
        }
    }

    fun getAddress(location: Location) {
        viewModelScope.launch {
            flow {
                val geocoder = Geocoder(MyApplication.applicationContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                Timber.e("address : ${address.first()}")
                emit(ApiResult.success(address.first().getAddressLine(0)))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _address.value = it }
        }
    }
}