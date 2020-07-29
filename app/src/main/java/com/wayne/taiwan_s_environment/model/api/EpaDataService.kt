package com.wayne.taiwan_s_environment.model.api

import com.wayne.taiwan_s_environment.model.api.vo.AQI
import com.wayne.taiwan_s_environment.model.api.vo.EpaResponse
import com.wayne.taiwan_s_environment.model.api.vo.UV
import retrofit2.http.GET
import retrofit2.http.Query

interface EpaDataService {
    @GET("api/v1/uv_s_01")
    suspend fun getUV(@Query("limit") limit: Int,
                      @Query("offset") offset: Int,
                      @Query("api_key") apiKey: String,
                      @Query("format") format: String): EpaResponse<UV>

    @GET("api/v1/aqx_p_432")
    suspend fun getAQI(@Query("limit") limit: Int,
                       @Query("offset") offset: Int,
                       @Query("api_key") apiKey: String,
                       @Query("format") format: String): EpaResponse<AQI>
}