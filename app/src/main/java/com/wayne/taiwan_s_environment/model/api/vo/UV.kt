package com.wayne.taiwan_s_environment.model.api.vo

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

/**
 * https://data.epa.gov.tw/dataset/uv_s_01/resource/c7438756-1c57-4e67-a857-7caef67ec973
 * */
data class UV(
    @SerializedName("county") val county: String,
    @SerializedName("unit") val publishAgency: String,
    @SerializedName("datacreationdate") val publishTime: String,
    @SerializedName("sitename") val siteName: String,
    @SerializedName("uvi") val uvi: String,
    @SerializedName("wgs84_lat") val wgs84Lat: String,
    @SerializedName("wgs84_lon") val wgs84Lon: String
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