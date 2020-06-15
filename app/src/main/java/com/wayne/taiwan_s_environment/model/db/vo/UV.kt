package com.wayne.taiwan_s_environment.model.db.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uv")
data class UV(
    @PrimaryKey var siteName: String,
    @ColumnInfo(name = "county") var county: String? = null,
    @ColumnInfo(name = "publishAgency") var publishAgency: String? = null,
    @ColumnInfo(name = "publishTime") var publishTime: String? = null,
    @ColumnInfo(name = "uvi") var uvi: String? = null,
    @ColumnInfo(name = "wgs84Lat") var wgs84Lat: String? = null,
    @ColumnInfo(name = "wgs84Lon") var wgs84Lon: String? = null
)