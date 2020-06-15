package com.wayne.taiwan_s_environment.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.api.OpenDataService
import com.wayne.taiwan_s_environment.model.api.vo.UV
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.pref.Pref
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber
import java.util.*

class SplashViewModel : BaseViewModel() {

    private val openDateService: OpenDataService by inject()
    private val uvDao: UVDao by inject()
    private val pref: Pref by inject()

    private val _uvList = MutableLiveData<ApiResult<List<UV>>>()
    val uvList: LiveData<ApiResult<List<UV>>>
        get() = _uvList

    val isFirstStartApp: Boolean = pref.isFirstStartApp
    var startTime: Long? = null

    fun getUV() {
        if (startTime == null) startTime = Date().time
        viewModelScope.launch {
            flow {
                Timber.e("before ${uvDao.getAll().size}")
                val result = openDateService.getUV()
                val body = result.body()
                if (!result.isSuccessful || body == null) throw HttpException(result)
                insertUVAll(body)
                Timber.e("after ${uvDao.getAll().size}")
                val delayTime = Date().time - startTime!!
                if (delayTime < 3000) {
                    delay(3000 - startTime!!)
                }
                emit(ApiResult.success(body))
            }.flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _uvList.value = it }
        }
    }

    private fun insertUVAll(uvs: List<UV>) {
        val list = arrayListOf<com.wayne.taiwan_s_environment.model.db.vo.UV>()
        for (uv in uvs) {
            val dbUV = com.wayne.taiwan_s_environment.model.db.vo.UV(uv.siteName,
                uv.county,
                uv.publishAgency,
                uv.publishTime,
                uv.uvi,
                uv.wgs84Lat,
                uv.wgs84Lon)
            list.add(dbUV)
        }
        uvDao.insertUVAll(list)
    }
}