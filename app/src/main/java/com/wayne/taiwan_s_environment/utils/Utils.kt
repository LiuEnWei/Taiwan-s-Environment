package com.wayne.taiwan_s_environment.utils

import android.content.Context

fun Float.toDp(context: Context): Float {
    val density = context.resources.displayMetrics.density
    return this * density
}

fun Float.toPx(context: Context): Float {
    val density = context.resources.displayMetrics.density
    return this / density
}