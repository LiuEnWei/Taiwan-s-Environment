package com.wayne.taiwan_s_environment.model.db.vo

import androidx.room.ColumnInfo
import java.io.Serializable

data class Home (@ColumnInfo(name = "siteName") val siteName: String? = null,
                 @ColumnInfo(name = "county") val county: String? = null,
                 @ColumnInfo(name = "publishAgency") val publishAgency: String? = null,
                 @ColumnInfo(name = "publishTime") val publishTime: String? = null,
                 @ColumnInfo(name = "uvi") val uvi: String? = null,
                 @ColumnInfo(name = "aqi") val aqi: String? = null,
                 @ColumnInfo(name = "pollutant") val pollutant: String? = null,
                 @ColumnInfo(name = "status") val status: String? = null,
                 @ColumnInfo(name = "s_o2") var SO2: String? = null,
                 @ColumnInfo(name = "c_o") val CO: String?,
                 @ColumnInfo(name = "c_o_8hr") val CO_8hr: String? = null,
                 @ColumnInfo(name = "o3") val O3: String? = null,
                 @ColumnInfo(name = "o3_8hr") val O3_8hr: String? = null,
                 @ColumnInfo(name = "pm10") val PM10: String? = null,
                 @ColumnInfo(name = "pm2_5") val PM2_5: String? = null,
                 @ColumnInfo(name = "n_o2") val NO2: String? = null,
                 @ColumnInfo(name = "n_ox") val NOx: String? = null,
                 @ColumnInfo(name = "n_o") val NO: String? = null,
                 @ColumnInfo(name = "windSpeed") val windSpeed: String? = null,
                 @ColumnInfo(name = "windDirec") val windDirec: String? = null,
                 @ColumnInfo(name = "pm2_5_avg") val PM2_5_AVG: String? = null,
                 @ColumnInfo(name = "pm10_avg") val PM10_AVG: String? = null,
                 @ColumnInfo(name = "s_o2_avg") val SO2_AVG: String? = null,
                 @ColumnInfo(name = "time") val time: Long? = null,
                 @ColumnInfo(name = "epaDataType") val epaDataType: Int): Comparable<Home>, Serializable {


    override fun compareTo(other: Home): Int {
        return if (time != null && time != other.time) {
            ((other.time ?: 0) - time).toInt()
        } else if (county != null && county != other.county) {
            county.compareTo(other.county?:"")
        } else {
            (epaDataType).compareTo(other.epaDataType)
        }
    }
}