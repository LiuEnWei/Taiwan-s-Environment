package com.wayne.taiwan_s_environment.di

import androidx.room.Room
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.MyApplication
import com.wayne.taiwan_s_environment.model.db.AppDatabase
import com.wayne.taiwan_s_environment.model.db.dao.AQIDao
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import org.koin.dsl.module

val dbModule = module {
    single { createAppDatabase() }
    single { createUVDao(get()) }
    single { createAQIDao(get()) }
}

fun createAppDatabase() : AppDatabase {
    return Room.databaseBuilder(
        MyApplication.applicationContext(),
        AppDatabase::class.java,
        Constant.DB_NAME
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}

fun createUVDao(appDatabase: AppDatabase): UVDao {
    return appDatabase.uvDao()
}

fun createAQIDao(appDatabase: AppDatabase): AQIDao {
    return appDatabase.aqiDao()
}