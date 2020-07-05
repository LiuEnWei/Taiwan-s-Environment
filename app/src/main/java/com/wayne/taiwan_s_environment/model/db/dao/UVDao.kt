package com.wayne.taiwan_s_environment.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.model.db.vo.UV

@Dao
interface UVDao {
    @Query("SELECT * FROM uv ORDER BY county")
    suspend fun getAll(): List<UV>

    @Query("SELECT siteName as siteName, county as county, publishAgency as publishAgency, publishTime as publishTime, uvi as uvi, time as time, ${Constant.EPA_DATA_TYPE_UV} as epaDataType  FROM uv WHERE county = :county ORDER BY time DESC")
    suspend fun getAllByCounty(county: String): List<Home>

    @Query("SELECT MAX(time) FROM uv")
    suspend fun getMaxTime(): Long?

    @Query("SELECT siteName as siteName, county as county, publishAgency as publishAgency, publishTime as publishTime, uvi as uvi, MAX(time) as time, ${Constant.EPA_DATA_TYPE_UV} as epaDataType FROM uv GROUP BY siteName")
    suspend fun getAllNewest(): List<Home>

    @Query("SELECT siteName as siteName, county as county, publishAgency as publishAgency, publishTime as publishTime, uvi as uvi, MAX(time) as time, ${Constant.EPA_DATA_TYPE_UV} as epaDataType FROM uv WHERE county = :county GROUP BY siteName")
    suspend fun getAllNewestByCounty(county: String): List<Home>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUV(uv: UV)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(uv: List<UV>)

    @Query("DELETE FROM uv WHERE time < :time")
    suspend fun deleteByTime(time: Long)

    @Query("DELETE FROM uv")
    suspend fun deleteAll()
}