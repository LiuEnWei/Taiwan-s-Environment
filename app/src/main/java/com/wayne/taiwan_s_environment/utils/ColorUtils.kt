package com.wayne.taiwan_s_environment.utils

import android.content.Context
import com.wayne.taiwan_s_environment.R

fun Context.getUVColor(uv: Int): Int {
    return when {
        uv in 0..2 -> resources.getColor(R.color.colorUVIndexLow)
        uv in 3..5 -> resources.getColor(R.color.colorUVIndexMiddleweight)
        uv in 6..7 -> resources.getColor(R.color.colorUVIndexHigh)
        uv in 8..10 -> resources.getColor(R.color.colorUVIndexExcess)
        uv >= 11 -> resources.getColor(R.color.colorUVIndexDangerous)
        else -> resources.getColor(R.color.colorGray600)
    }
}

fun Context.getAQIColor(aqi: Int): Int {
    return when (aqi) {
        in 0..50 -> resources.getColor(R.color.colorAQIGood)
        in 51..100 -> resources.getColor(R.color.colorAQIModerate)
        in 101..150 -> resources.getColor(R.color.colorAQIUnhealthyForSensitiveGroups)
        in 151..200 -> resources.getColor(R.color.colorAQIUnhealthy)
        in 201..300 -> resources.getColor(R.color.colorAQIVeryUnhealthy)
        in 301..500 -> resources.getColor(R.color.colorAQIHazardous)
        else -> resources.getColor(R.color.colorGray600)
    }
}