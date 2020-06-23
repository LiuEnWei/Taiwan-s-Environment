package com.wayne.taiwan_s_environment.model.db.vo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "uv", primaryKeys = ["siteName", "publishTime"])
data class UV(
    @ColumnInfo(name = "siteName") var siteName: String,
    @ColumnInfo(name = "county") var county: String,
    @ColumnInfo(name = "publishAgency") var publishAgency: String,
    @ColumnInfo(name = "publishTime") var publishTime: String,
    @ColumnInfo(name = "uvi") var uvi: String,
    @ColumnInfo(name = "wgs84Lat") var wgs84Lat: String,
    @ColumnInfo(name = "wgs84Lon") var wgs84Lon: String,
    @ColumnInfo(name = "time") var time: Long? = null
)