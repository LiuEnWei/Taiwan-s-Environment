package com.wayne.taiwan_s_environment.di

import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.api.EpaDataService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { createOkHttpClient() }
    single { createEpaDataService(get()) }
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