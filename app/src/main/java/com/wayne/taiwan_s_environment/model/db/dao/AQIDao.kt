package com.wayne.taiwan_s_environment.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.db.vo.AQI
import com.wayne.taiwan_s_environment.model.db.vo.Home

@Dao
interface AQIDao {
    @Query("SELECT * FROM aqi")
    suspend fun getAll(): List<AQI>

    @Query("SELECT siteName as siteName, county as county, publishTime as publishTime, aqi as aqi, pollutant as pollutant, status as status, s_o2 as s_o2, c_o as c_o, c_o_8hr as c_o_8hr, o3 as o3, o3_8hr as o3_8hr, pm10 as pm10, pm2_5 as pm2_5, n_o2 as n_o2, n_ox as n_ox, n_o as n_o, windSpeed as windSpeed, windDirec as windDirec, pm2_5_avg as pm2_5_avg, pm10_avg as pm10_avg, s_o2_avg as s_o2_avg, time as time, ${Constant.EPA_DATA_TYPE_AQI} as epaDataType  FROM aqi WHERE county = :county ORDER BY time DESC")
    suspend fun getAllByCounty(county: String): List<Home>

    @Query("SELECT MAX(time) FROM uv")
    suspend fun getMaxTime(): Long?

    @Query("SELECT siteName as siteName, county as county, publishTime as publishTime, aqi as aqi, pollutant as pollutant, status as status, s_o2 as s_o2, c_o as c_o, c_o_8hr as c_o_8hr, o3 as o3, o3_8hr as o3_8hr, pm10 as pm10, pm2_5 as pm2_5, n_o2 as n_o2, n_ox as n_ox, n_o as n_o, windSpeed as windSpeed, windDirec as windDirec, pm2_5_avg as pm2_5_avg, pm10_avg as pm10_avg, s_o2_avg as s_o2_avg, MAX(time) as time, ${Constant.EPA_DATA_TYPE_AQI} as epaDataType FROM aqi GROUP BY siteName")
    suspend fun getAllNewest(): List<Home>

    @Query("SELECT siteName as siteName, county as county, publishTime as publishTime, aqi as aqi, pollutant as pollutant, status as status, s_o2 as s_o2, c_o as c_o, c_o_8hr as c_o_8hr, o3 as o3, o3_8hr as o3_8hr, pm10 as pm10, pm2_5 as pm2_5, n_o2 as n_o2, n_ox as n_ox, n_o as n_o, windSpeed as windSpeed, windDirec as windDirec, pm2_5_avg as pm2_5_avg, pm10_avg as pm10_avg, s_o2_avg as s_o2_avg, MAX(time) as time, ${Constant.EPA_DATA_TYPE_AQI} as epaDataType FROM aqi WHERE county = :county GROUP BY siteName")
    suspend fun getAllNewestByCounty(county: String): List<Home>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(uv: List<AQI>)

    @Query("DELETE FROM aqi WHERE time < :time")
    suspend fun deleteByTime(time: Long)

    @Query("DELETE FROM aqi")
    suspend fun deleteAll()
}