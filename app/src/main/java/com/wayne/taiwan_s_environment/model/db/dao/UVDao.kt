package com.wayne.taiwan_s_environment.model.db.dao

import androidx.room.*
import com.wayne.taiwan_s_environment.model.db.vo.UV

@Dao
interface UVDao {
    @Query("SELECT * FROM uv ORDER BY county")
    fun getAll(): List<UV>

    @Query("SELECT * FROM uv WHERE county = :county ORDER BY time DESC")
    fun getAllByCounty(county: String): List<UV>

    @Query("SELECT MAX(time) FROM uv")
    fun getMaxTime(): Long

    @Query("SELECT * FROM uv GROUP BY siteName ORDER BY county")
    fun getAllNewest(): List<UV>

    @Query("SELECT siteName as siteName, county as county, publishAgency as publishAgency, publishTime as publishTime, uvi as uvi, wgs84Lat as wgs84Lat, wgs84Lon as wgs84Lon, MAX(time) as time FROM uv WHERE county = :county GROUP BY siteName")
    fun getAllNewestByCounty(county: String): List<UV>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUV(uv: UV)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUVAll(uv: List<UV>)
}