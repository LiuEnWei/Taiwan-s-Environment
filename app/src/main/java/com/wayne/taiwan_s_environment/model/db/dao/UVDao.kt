package com.wayne.taiwan_s_environment.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wayne.taiwan_s_environment.model.db.vo.UV

@Dao
interface UVDao {
    @Query("SELECT * FROM uv ORDER BY county")
    fun getAll(): List<UV>

    @Query("SELECT * FROM uv WHERE county = :county ORDER BY time DESC")
    fun getAllByCounty(county: String): List<UV>

    @Query("SELECT MAX(time) FROM uv")
    fun getMaxTime(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUV(uv: UV)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUVAll(uv: List<UV>)
}