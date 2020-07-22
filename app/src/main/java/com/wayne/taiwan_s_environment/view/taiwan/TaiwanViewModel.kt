package com.wayne.taiwan_s_environment.view.taiwan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.inject

class TaiwanViewModel : BaseViewModel() {

    private val repository: Repository by inject()

    private val _epaList = MutableLiveData<ApiResult<ArrayList<Home>>>()
    val epaList: LiveData<ApiResult<ArrayList<Home>>>
        get() = _epaList

    fun getUVByCounty(county: String? = null) {
        viewModelScope.launch {
            flow {
                val list = repository.getAllNewestByCounty(county)
                emit(ApiResult.success(list))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _epaList.postValue(it) }
        }
    }
}