package com.wayne.taiwan_s_environment.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.api.EpaDataService
import com.wayne.taiwan_s_environment.model.db.dao.AQIDao
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.utils.toDbAQIList
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
    private val aqiDao: AQIDao by inject()

    private val _uvList = MutableLiveData<ApiResult<Nothing>>()
    val uvList: LiveData<ApiResult<Nothing>>
        get() = _uvList

    private var startTime: Long? = null
    private var oneHour = 60 * 60 * 1000
    private var minDelayTime = 7000L

    fun getEpaData() {
        viewModelScope.launch {
            flow {
                if (startTime == null){
                    startTime = Date().time
                    uvDao.deleteByTime(getTodayStart())
                    aqiDao.deleteByTime(getTodayStart())
                }

                val maxTime = uvDao.getMaxTime().coerceAtMost(aqiDao.getMaxTime()).let {
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
                    emit(ApiResult.success(null))
                } else {
                    val hourCount = (nowTime - maxTime) / oneHour
                    val uvLimit = hourCount * Constant.EPA_DATA_UV_SITE_COUNTS
                    val uvResult = epaDateService.getUV(limit = uvLimit.toInt())
                    val uvRecords = uvResult.body()?.records
                    if (!uvResult.isSuccessful || uvRecords == null) throw HttpException(uvResult)
                    uvDao.insertAll(uvRecords.toDbUVList())

                    val aqiLimit = hourCount * Constant.EPA_DATA_AQI_SITE_COUNTS
                    val aqiResult = epaDateService.getAQI(limit = aqiLimit.toInt())
                    val aqiRecords = aqiResult.body()?.records
                    if (!aqiResult.isSuccessful || aqiRecords == null) throw HttpException(uvResult)
                    aqiDao.insertAll(aqiRecords.toDbAQIList())

                    val delayTime = Date().time - startTime!!
                    if (delayTime < minDelayTime) {
                        delay(minDelayTime - delayTime)
                    }

                    emit(ApiResult.success(null))
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
}