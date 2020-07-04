package com.wayne.taiwan_s_environment.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wayne.taiwan_s_environment.model.db.dao.AQIDao
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.db.vo.AQI
import com.wayne.taiwan_s_environment.model.db.vo.UV

@Database(entities = [UV::class, AQI::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun uvDao(): UVDao
    abstract fun aqiDao(): AQIDao
}