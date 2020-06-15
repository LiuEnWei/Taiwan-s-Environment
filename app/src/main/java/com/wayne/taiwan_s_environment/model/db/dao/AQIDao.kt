package com.wayne.taiwan_s_environment.model.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.wayne.taiwan_s_environment.model.db.vo.AQI

@Dao
interface AQIDao {
    @Query("SELECT * FROM aqi")
    fun getAll(): List<AQI>
}