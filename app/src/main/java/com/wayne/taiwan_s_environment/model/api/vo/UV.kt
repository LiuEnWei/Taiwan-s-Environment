package com.wayne.taiwan_s_environment.model.api.vo

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class UV(
    @SerializedName("County") val county: String,
    @SerializedName("PublishAgency") val publishAgency: String,
    @SerializedName("PublishTime") val publishTime: String,
    @SerializedName("SiteName") val siteName: String,
    @SerializedName("UVI") val uvi: String,
    @SerializedName("WGS84Lat") val wgs84Lat: String,
    @SerializedName("WGS84Lon") val wgs84Lon: String
) {
    val PUBLISH_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
}