package com.wayne.taiwan_s_environment.utils

import com.wayne.taiwan_s_environment.model.api.vo.AQI
import com.wayne.taiwan_s_environment.model.api.vo.UV

fun List<UV>.toDbUVList(): List<com.wayne.taiwan_s_environment.model.db.vo.UV> {
    val list = arrayListOf<com.wayne.taiwan_s_environment.model.db.vo.UV>()
    for (uv in this) {
        list.add(uv.toDbUV())
    }
    return list
}

fun List<AQI>.toDbAQIList(): List<com.wayne.taiwan_s_environment.model.db.vo.AQI> {
    val list = arrayListOf<com.wayne.taiwan_s_environment.model.db.vo.AQI>()
    for (uv in this) {
        list.add(uv.toDbAQI())
    }
    return list
}