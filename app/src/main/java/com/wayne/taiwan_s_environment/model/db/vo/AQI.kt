package com.wayne.taiwan_s_environment.model.db.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aqi")
data class AQI(
    @PrimaryKey var siteName: String,
    @ColumnInfo(name = "siteId") var siteId: String,
    @ColumnInfo(name = "county") var county: String? = null,
    @ColumnInfo(name = "aqi") var aqi: String? = null,
    @ColumnInfo(name = "pollutant") var pollutant: String? = null,
    @ColumnInfo(name = "status") var status: String? = null,
    @ColumnInfo(name = "SO2") var SO2: String? = null,
    @ColumnInfo(name = "CO") var CO: String? = null,
    @ColumnInfo(name = "CO_8hr") var CO_8hr: String? = null,
    @ColumnInfo(name = "O3_8hr") var O3_8hr: String? = null,
    @ColumnInfo(name = "PM10") var PM10: String? = null,
    @ColumnInfo(name = "PM2_5") var PM2_5: String? = null,
    @ColumnInfo(name = "NO2") var NO2: String? = null,
    @ColumnInfo(name = "NOx") var NOx: String? = null,
    @ColumnInfo(name = "NO") var NO: String? = null,
    @ColumnInfo(name = "windSpeed") var windSpeed: String? = null,
    @ColumnInfo(name = "windDirec") var windDirec: String? = null,
    @ColumnInfo(name = "publishTime") var publishTime: String? = null,
    @ColumnInfo(name = "PM2_5_AVG") var PM2_5_AVG: String? = null,
    @ColumnInfo(name = "PM10_AVG") var PM10_AVG: String? = null,
    @ColumnInfo(name = "SO2_AVG") var SO2_AVG: String? = null,
    @ColumnInfo(name = "longitude") var longitude: String? = null,
    @ColumnInfo(name = "latitude") var latitude: String? = null
)