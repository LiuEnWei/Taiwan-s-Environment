package com.wayne.taiwan_s_environment.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.api.OpenDataService
import com.wayne.taiwan_s_environment.model.api.vo.UV
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException

class HomeViewModel : BaseViewModel() {

    private val openDateService: OpenDataService by inject()

    private val _uvList = MutableLiveData<ApiResult<List<UV>>>()
    val uvList: LiveData<ApiResult<List<UV>>>
        get() = _uvList

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


}