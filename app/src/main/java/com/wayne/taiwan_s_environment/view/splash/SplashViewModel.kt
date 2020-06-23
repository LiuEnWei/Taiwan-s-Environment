package com.wayne.taiwan_s_environment.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.api.EpaDataService
import com.wayne.taiwan_s_environment.model.api.vo.UV
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.utils.toDbUVList
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber
import java.util.*

class SplashViewModel : BaseViewModel() {

    private val epaDateService: EpaDataService by inject()
    private val uvDao: UVDao by inject()

    private val _uvList = MutableLiveData<ApiResult<List<UV>>>()
    val uvList: LiveData<ApiResult<List<UV>>>
        get() = _uvList

    private var startTime: Long? = null
    private var oneHour = 60 * 60 * 1000
    private var minDelayTime = 7000L

    fun getOpenUV() {
        if (startTime == null) startTime = Date().time
        viewModelScope.launch {
            flow {
                val maxTime = uvDao.getMaxTime()
                Timber.e("local max publish time : $maxTime")
                if (Date().time - maxTime < oneHour) {
                    // not need update
                    delay(minDelayTime)
                    emit(ApiResult.success(arrayListOf()))
                } else {
                    val result = epaDateService.getUV()
                    val records = result.body()?.records
                    if (!result.isSuccessful || records == null) throw HttpException(result)

                    uvDao.insertUVAll(records.toDbUVList())

                    val delayTime = Date().time - startTime!!
                    if (delayTime < minDelayTime) {
                        delay(minDelayTime - delayTime)
                    }

                    for (uv in records) {
                        Timber.e("$uv")
                    }
                    emit(ApiResult.success(records))
                }
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _uvList.value = it }
        }
    }
}