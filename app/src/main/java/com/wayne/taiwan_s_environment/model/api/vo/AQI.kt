package com.wayne.taiwan_s_environment.model.api.vo

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
/**
 * https://data.epa.gov.tw/dataset/aqx_p_432/resource/8ff027dc-2da2-42e8-85de-78ac3faf470e#
 * */
data class AQI(
    @SerializedName("SiteName") val siteName: String,
    @SerializedName("County") val county: String,
    @SerializedName("AQI") val aqi: String,
    @SerializedName("Pollutant") val pollutant: String = "",
    @SerializedName("Status") val status: String,
    @SerializedName("SO2") val SO2: String,
    @SerializedName("CO") val CO: String,
    @SerializedName("CO_8hr") val CO_8hr: String,
    @SerializedName("O3") val O3: String,
    @SerializedName("O3_8hr") val O3_8hr: String,
    @SerializedName("PM10") val PM10: String,
    @SerializedName("PM2.5") val PM2_5: String,
    @SerializedName("NO2") val NO2: String,
    @SerializedName("NOx") val NOx: String,
    @SerializedName("NO") val NO: String,
    @SerializedName("WindSpeed") val windSpeed: String,
    @SerializedName("WindDirec") val windDirec: String,
    @SerializedName("PublishTime") val publishTime: String,
    @SerializedName("PM2.5_AVG") val PM2_5_AVG: String,
    @SerializedName("PM10_AVG") val PM10_AVG: String,
    @SerializedName("SO2_AVG") val SO2_AVG: String,
    @SerializedName("Longitude") val longitude: String,
    @SerializedName("Latitude") val latitude: String,
    @SerializedName("SiteId") val siteId: String
) {
    companion object {
        val PUBLISH_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
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
            windSpeed,
            windDirec,
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