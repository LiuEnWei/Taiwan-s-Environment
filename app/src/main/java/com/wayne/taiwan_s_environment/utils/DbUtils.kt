package com.wayne.taiwan_s_environment.utils

import com.wayne.taiwan_s_environment.model.api.vo.UV

fun List<UV>.toDbUVList(): List<com.wayne.taiwan_s_environment.model.db.vo.UV> {
    val list = arrayListOf<com.wayne.taiwan_s_environment.model.db.vo.UV>()
    for (uv in this) {
        list.add(uv.toDbUV())
    }
    return list
}