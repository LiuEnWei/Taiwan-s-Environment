package com.wayne.taiwan_s_environment.view.taiwan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.db.dao.AQIDao
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
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

    private val uvDao: UVDao by inject()
    private val aqiDao: AQIDao by inject()

    private val _epaList = MutableLiveData<ApiResult<ArrayList<Home>>>()
    val epaList: LiveData<ApiResult<ArrayList<Home>>>
        get() = _epaList

    fun getUVByCounty(county: String? = null) {
        viewModelScope.launch {
            flow {
                val list = arrayListOf<Home>()

                list.addAll(if (county == null) {
                    uvDao.getAllNewest()
                } else {
                    uvDao.getAllNewestByCounty(county)
                })

                list.addAll(if (county == null) {
                    aqiDao.getAllNewest()
                } else {
                    aqiDao.getAllNewestByCounty(county)
                })
                list.sort()
                emit(ApiResult.success(list))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _epaList.postValue(it) }
        }
    }
}