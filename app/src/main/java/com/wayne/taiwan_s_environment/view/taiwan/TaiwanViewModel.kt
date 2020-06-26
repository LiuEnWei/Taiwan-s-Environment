package com.wayne.taiwan_s_environment.view.taiwan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.db.vo.UV
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.inject

class TaiwanViewModel : BaseViewModel() {

    private val uvDao: UVDao by inject()

    private val _uvList = MutableLiveData<ApiResult<List<UV>>>()
    val uvList: LiveData<ApiResult<List<UV>>>
        get() = _uvList

    fun getUVByCounty(county: String? = null) {
        viewModelScope.launch {
            flow {
                val uvList = if (county == null) {
                    uvDao.getAllNewest()
                } else {
                    uvDao.getAllNewestByCounty(county)
                }
                emit(ApiResult.success(uvList))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _uvList.value = it }
        }
    }
}