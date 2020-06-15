package com.wayne.taiwan_s_environment.model.api

import com.wayne.taiwan_s_environment.model.api.vo.AQI
import com.wayne.taiwan_s_environment.model.api.vo.UV
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenDataService {
    @GET("webapi/Data/UV/")
    suspend fun getUV(@Query("format") format: String = "json"): Response<List<UV>>

    @GET("webapi/Data/AQI/")
    suspend fun getAQI(@Query("format") format: String = "json"): Response<List<AQI>>
}