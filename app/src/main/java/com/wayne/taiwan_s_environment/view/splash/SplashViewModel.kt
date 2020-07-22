package com.wayne.taiwan_s_environment.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.model.api.ApiResult
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

    private val repository: Repository by inject()

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
                    emit(ApiResult.success(null))
                } else {
                    val hourCount = if (maxTime == nowTime) {
                        1
                    } else {
                        (nowTime - maxTime) / oneHour
                    }
                    Timber.e("need update, hourCount : $hourCount")
                    val uvLimit = hourCount * Constant.EPA_DATA_UV_SITE_COUNTS
                    val uvResult = repository.getUV(limit = uvLimit.toInt())
                    val uvRecords = uvResult.body()?.records
                    if (!uvResult.isSuccessful || uvRecords == null) throw HttpException(uvResult)
                    repository.insertUV(uvRecords.toDbUVList())

                    val aqiLimit = hourCount * Constant.EPA_DATA_AQI_SITE_COUNTS
                    val aqiResult = repository.getAQI(limit = aqiLimit.toInt())
                    val aqiRecords = aqiResult.body()?.records
                    if (!aqiResult.isSuccessful || aqiRecords == null) throw HttpException(uvResult)
                    repository.insertAQI(aqiRecords.toDbAQIList())

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