package com.wayne.taiwan_s_environment.utils

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import com.wayne.taiwan_s_environment.R


fun Context.getResColor(@ColorRes id: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(id)
    } else {
        resources.getColor(id)
    }
}

fun Context.getUVColor(uv: Int): Int {
    return when {
        uv in 0..2 -> getResColor(R.color.colorUVIndexLow)
        uv in 3..5 -> getResColor(R.color.colorUVIndexMiddleweight)
        uv in 6..7 -> getResColor(R.color.colorUVIndexHigh)
        uv in 8..10 -> getResColor(R.color.colorUVIndexExcess)
        uv >= 11 -> getResColor(R.color.colorUVIndexDangerous)
        else -> getResColor(R.color.colorGray600)
    }
}

fun Context.getAQIColor(aqi: Int): Int {
    return when (aqi) {
        in 0..50 -> getResColor(R.color.colorAQIGood)
        in 51..100 -> getResColor(R.color.colorAQIModerate)
        in 101..150 -> getResColor(R.color.colorAQIUnhealthyForSensitiveGroups)
        in 151..200 -> getResColor(R.color.colorAQIUnhealthy)
        in 201..300 -> getResColor(R.color.colorAQIVeryUnhealthy)
        in 301..500 -> getResColor(R.color.colorAQIHazardous)
        else -> getResColor(R.color.colorGray600)
    }
}