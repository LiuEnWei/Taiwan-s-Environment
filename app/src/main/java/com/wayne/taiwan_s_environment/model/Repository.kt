package com.wayne.taiwan_s_environment.model

import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.api.EpaDataService
import com.wayne.taiwan_s_environment.model.api.vo.AQI
import com.wayne.taiwan_s_environment.model.api.vo.EpaResponse
import com.wayne.taiwan_s_environment.model.api.vo.UV
import com.wayne.taiwan_s_environment.model.db.dao.AQIDao
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.model.pref.Pref
import retrofit2.Response

class Repository(private val epaDataService: EpaDataService, private val uvDao: UVDao, private val aqiDao: AQIDao, private val pref: Pref) {

    suspend fun getUV(limit: Int = Constant.EPA_DATA_UV_SITE_COUNTS,
                      offset: Int = 0,
                      apiKey: String = Constant.EPA_DATA_API_KEY,
                      format: String = "json"): Response<EpaResponse<UV>> {
        return epaDataService.getUV(limit, offset, apiKey, format)
    }

    suspend fun getAQI(limit: Int = Constant.EPA_DATA_AQI_SITE_COUNTS,
                       offset: Int = 0,
                       apiKey: String = Constant.EPA_DATA_API_KEY,
                       format: String = "json"): Response<EpaResponse<AQI>> {
        return epaDataService.getAQI(limit, offset, apiKey, format)
    }

    suspend fun insertUV(uv: List<com.wayne.taiwan_s_environment.model.db.vo.UV>) {
        uvDao.insertAll(uv)
    }

    suspend fun insertAQI(aqi: List<com.wayne.taiwan_s_environment.model.db.vo.AQI>) {
        aqiDao.insertAll(aqi)
    }

    suspend fun getAllByCounty(county: String): ArrayList<Home> {
        val list = arrayListOf<Home>()
        list.addAll(uvDao.getAllByCounty(county))
        list.addAll(aqiDao.getAllByCounty(county))
        list.sort()
        return list
    }

    suspend fun getMaxTime(): Long {
        val uvMaxTime = uvDao.getMaxTime()?:0
        val aqiMaxTime = aqiDao.getMaxTime()?:0
        return (uvMaxTime).coerceAtMost(aqiMaxTime)
    }

    suspend fun getUVMaxTime(): Long {
        return uvDao.getMaxTime()?:0
    }

    suspend fun getAQIMaxTime(): Long {
        return aqiDao.getMaxTime()?:0
    }

    suspend fun getAllNewestByCounty(county: String?): ArrayList<Home> {
        val list = arrayListOf<Home>()
        if (county != null) {
            list.addAll(uvDao.getAllNewestByCounty(county))
            list.addAll(aqiDao.getAllNewestByCounty(county))
        } else {
            list.addAll(uvDao.getAllNewest())
            list.addAll(aqiDao.getAllNewest())
        }
        list.sort()
        return list
    }

    suspend fun deleteByTime(time: Long) {
        uvDao.deleteByTime(time)
        aqiDao.deleteByTime(time)
    }

    fun isFirstStartApp(): Boolean {
        return pref.isFirstStartApp
    }

    fun setFirstStartApp(isFirstStartApp: Boolean) {
        pref.isFirstStartApp = isFirstStartApp
    }

    fun getPrefCounty(): String? {
        return pref.county
    }

    fun setPrefCounty(county: String) {
        pref.county = county
    }

    fun isPowerSaving(): Boolean {
        return pref.isPowerSaving
    }

    fun setPowerSaving(isPowerSaving: Boolean) {
        pref.isPowerSaving = isPowerSaving
    }
}