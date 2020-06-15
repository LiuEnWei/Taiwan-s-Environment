package com.wayne.taiwan_s_environment.di

import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.api.OpenDataService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { createOkHttpClient() }
    single { createOpenDataService(get()) }
}

fun createOkHttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

fun createOpenDataService(okHttpClient: OkHttpClient): OpenDataService {
    val retrofit =  Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constant.OPEN_DATA_URL)
        .build()
    return retrofit.create(OpenDataService::class.java)
}