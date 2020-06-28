package com.wayne.taiwan_s_environment.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.Constant
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
                val maxTime = uvDao.getMaxTime().let {
                    val today = getTodayStart()
                    return@let if (it == 0L || it < today) {
                        today
                    } else {
                        it
                    }
                }

                Timber.e("local max publish time : $maxTime")
                val nowTime = getNow()
                Timber.e("nowTime : $nowTime")
                Timber.e("nowTime : ${Date().time}")
                if (nowTime - maxTime < oneHour) {
                    // not need update
                    delay(minDelayTime)
                    emit(ApiResult.success(arrayListOf()))
                } else {
                    val limit = (nowTime - maxTime) / oneHour * Constant.EPA_DATA_UV_SITE_COUNTS
                    val result = epaDateService.getUV(limit = limit.toInt())
                    val records = result.body()?.records
                    if (!result.isSuccessful || records == null) throw HttpException(result)

                    uvDao.insertUVAll(records.toDbUVList())

                    val delayTime = Date().time - startTime!!
                    if (delayTime < minDelayTime) {
                        delay(minDelayTime - delayTime)
                    }

                    Timber.e("${records.size}")
                    emit(ApiResult.success(records))
                }
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _uvList.value = it }
        }
    }

    private fun getTodayStart(): Long {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"))
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun getNow(): Long {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"))
        return cal.timeInMillis
    }

    fun cleanOldData() {
        viewModelScope.launch {
            uvDao.deleteByTime(getTodayStart())
        }
    }
}