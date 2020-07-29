package com.wayne.taiwan_s_environment.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.model.Result
import com.wayne.taiwan_s_environment.utils.toDbAQIList
import com.wayne.taiwan_s_environment.utils.toDbUVList
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.inject
import timber.log.Timber
import java.util.*

class SplashViewModel : BaseViewModel() {

    private val repository: Repository by inject()

    private val _uvList = MutableLiveData<Result<Nothing>>()
    val uvList: LiveData<Result<Nothing>>
        get() = _uvList

    private var startTime: Long? = null
    private var oneHour = 60 * 60 * 1000
    private var minDelayTime = 7000L

    fun getEpaData() {
        viewModelScope.launch {
            if (startTime == null){
                startTime = Date().time
                repository.deleteByTime(getTodayStart())
            }

            val maxTime = repository.getMaxTime().let {
                val today = getTodayStart()
                return@let if (it == 0L || it < today) {
                    today
                } else {
                    it
                }
            }

            Timber.e("local max publish time : $maxTime")
            val nowTime = getNowHourTime()
            Timber.e("nowHourTime : $nowTime")
            if (repository.getUVMaxTime() != 0L && repository.getAQIMaxTime() != 0L && nowTime - maxTime < oneHour) {
                // not need update
                Timber.e("not need update")
                delay(minDelayTime)
                _uvList.value = Result.success(null)
            } else {
                val hourCount = if (maxTime == nowTime) {
                    1
                } else {
                    (nowTime - maxTime) / oneHour
                }
                Timber.e("need update, hourCount : $hourCount")
                val uvLimit = hourCount * Constant.EPA_DATA_UV_SITE_COUNTS
                val uvResult = repository.getUV(limit = uvLimit.toInt())
                if (uvResult is Result.Success) {
                    repository.insertUV(uvResult.result.toDbUVList())
                }

                val aqiLimit = hourCount * Constant.EPA_DATA_AQI_SITE_COUNTS
                val aqiResult = repository.getAQI(limit = aqiLimit.toInt())
                if (aqiResult is Result.Success) {
                    repository.insertAQI(aqiResult.result.toDbAQIList())
                }

                val delayTime = Date().time - startTime!!
                if (delayTime < minDelayTime) {
                    delay(minDelayTime - delayTime)
                }

                _uvList.value = when {
                    uvResult is Result.Error -> {
                        Result.error(uvResult.throwable)
                    }

                    aqiResult is Result.Error -> {
                        Result.error(aqiResult.throwable)
                    }

                    else -> {
                        Result.success(null)
                    }
                }

            }
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

    private fun getNowHourTime(): Long {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"))
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun isFirstStartApp(): Boolean {
        return repository.isFirstStartApp()
    }
}