package com.wayne.taiwan_s_environment.di

import androidx.room.Room
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.MyApplication
import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.model.api.EpaDataService
import com.wayne.taiwan_s_environment.model.db.AppDatabase
import com.wayne.taiwan_s_environment.model.db.dao.AQIDao
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.pref.Pref
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { createPref() }
    single { createOkHttpClient() }
    single { createEpaDataService(get()) }
    single { createAppDatabase() }
    single { createUVDao(get()) }
    single { createAQIDao(get()) }
    single { createRepository(get(), get(), get(), get()) }
}

fun createPref() : Pref {
    return Pref(Constant.PREFS_NAME)
}


fun createOkHttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
}

fun createEpaDataService(okHttpClient: OkHttpClient): EpaDataService {
    val retrofit =  Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constant.EPA_DATA_URL)
        .build()
    return retrofit.create(EpaDataService::class.java)
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

fun createRepository(epaDataService: EpaDataService, uvDao: UVDao, aqiDao: AQIDao, pref: Pref): Repository {
    return Repository(epaDataService, uvDao, aqiDao, pref)
}