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

    fun toDbUV(): com.wayne.taiwan_s_environment.model.db.vo.UV {
        return com.wayne.taiwan_s_environment.model.db.vo.UV(siteName,
            county,
            publishAgency,
            publishTime,
            uvi,
            wgs84Lat,
            wgs84Lon,
            getTime())
    }
}