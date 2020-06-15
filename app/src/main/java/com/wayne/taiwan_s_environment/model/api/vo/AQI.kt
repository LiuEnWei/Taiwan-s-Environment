package com.wayne.taiwan_s_environment.model.api.vo

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class AQI(
    @SerializedName("SiteName") val siteName: String,
    @SerializedName("County") val county: String,
    @SerializedName("AQI") val aqi: String,
    @SerializedName("Pollutant") val pollutant: String,
    @SerializedName("Status") val status: String,
    @SerializedName("SO2") val SO2: String,
    @SerializedName("CO") val CO: String,
    @SerializedName("CO_8hr") val CO_8hr: String,
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
    val PUBLISH_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
}