package com.wayne.taiwan_s_environment.model.span

import android.text.TextPaint
import android.text.style.RelativeSizeSpan
import androidx.annotation.ColorInt

class BoldColorSpan(@ColorInt val color: Int, proportion: Float = 1f): RelativeSizeSpan(proportion) {
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isFakeBoldText = true
        ds.color = color
    }
}