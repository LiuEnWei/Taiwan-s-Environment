package com.wayne.taiwan_s_environment.model.api.vo

import com.google.gson.annotations.SerializedName

data class EpaResponse<T> (
    @SerializedName("include_total") val includeTotal: Boolean,
    @SerializedName("resource_id") val resourceId: String,
    @SerializedName("records") val records: List<T> = arrayListOf()
)