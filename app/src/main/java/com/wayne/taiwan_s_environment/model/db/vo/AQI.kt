package com.wayne.taiwan_s_environment.model.db.vo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "aqi", primaryKeys = ["siteName", "publishTime"])
data class AQI(
    @ColumnInfo(name = "siteName") var siteName: String,
    @ColumnInfo(name = "county") var county: String,
    @ColumnInfo(name = "aqi") var aqi: String,
    @ColumnInfo(name = "pollutant") var pollutant: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "s_o2") var SO2: String,
    @ColumnInfo(name = "c_o") var CO: String,
    @ColumnInfo(name = "c_o_8hr") var CO_8hr: String,
    @ColumnInfo(name = "o3") var O3: String,
    @ColumnInfo(name = "o3_8hr") var O3_8hr: String,
    @ColumnInfo(name = "pm10") var PM10: String,
    @ColumnInfo(name = "pm2_5") var PM2_5: String,
    @ColumnInfo(name = "n_o2") var NO2: String,
    @ColumnInfo(name = "n_ox") var NOx: String,
    @ColumnInfo(name = "n_o") var NO: String,
    @ColumnInfo(name = "windSpeed") var windSpeed: String,
    @ColumnInfo(name = "windDirec") var windDirec: String,
    @ColumnInfo(name = "publishTime") var publishTime: String,
    @ColumnInfo(name = "pm2_5_avg") var PM2_5_AVG: String,
    @ColumnInfo(name = "pm10_avg") var PM10_AVG: String,
    @ColumnInfo(name = "s_o2_avg") var SO2_AVG: String,
    @ColumnInfo(name = "longitude") var longitude: String,
    @ColumnInfo(name = "latitude") var latitude: String,
    @ColumnInfo(name = "siteId") var siteId: String,
    @ColumnInfo(name = "time") var time: Long? = null
)