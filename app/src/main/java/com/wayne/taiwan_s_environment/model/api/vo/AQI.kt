package com.wayne.taiwan_s_environment.model.api.vo

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

/**
 * https://data.epa.gov.tw/dataset/aqx_p_432/resource/8ff027dc-2da2-42e8-85de-78ac3faf470e#
 * */
data class AQI(
    @SerializedName("sitename") val siteName: String,
    @SerializedName("county") val county: String,
    @SerializedName("aqi") val aqi: String,
    @SerializedName("pollutant") val pollutant: String = "",
    @SerializedName("status") val status: String,
    @SerializedName("so2") val SO2: String,
    @SerializedName("co") val CO: String,
    @SerializedName("co_8hr") val CO_8hr: String,
    @SerializedName("o3") val O3: String,
    @SerializedName("o3_8hr") val O3_8hr: String,
    @SerializedName("pm10") val PM10: String,
    @SerializedName("pm2.5") val PM2_5: String,
    @SerializedName("no2") val NO2: String,
    @SerializedName("nox") val NOx: String,
    @SerializedName("no") val NO: String,
    @SerializedName("wind_speed") val windSpeed: String?,
    @SerializedName("wind_direc") val windDirec: String?,
    @SerializedName("publishtime") val publishTime: String,
    @SerializedName("pm2.5_avg") val PM2_5_AVG: String,
    @SerializedName("pm10_avg") val PM10_AVG: String,
    @SerializedName("so2_avg") val SO2_AVG: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("siteid") val siteId: String
) {
    companion object {
        val PUBLISH_TIME_FORMAT = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.TAIWAN)
    }

    private fun getTime(): Long? {
        return try {
            PUBLISH_TIME_FORMAT.parse(publishTime)?.time
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun toDbAQI(): com.wayne.taiwan_s_environment.model.db.vo.AQI {
        return com.wayne.taiwan_s_environment.model.db.vo.AQI(siteName,
            county,
            aqi,
            pollutant,
            status,
            SO2,
            CO,
            CO_8hr,
            O3,
            O3_8hr,
            PM10,
            PM2_5,
            NO2,
            NOx,
            NO,
            windSpeed ?: "",
            windDirec ?: "",
            publishTime,
            PM2_5_AVG,
            PM10_AVG,
            SO2_AVG,
            longitude,
            latitude,
            siteId,
            getTime())
    }
}