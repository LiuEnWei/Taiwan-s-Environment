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