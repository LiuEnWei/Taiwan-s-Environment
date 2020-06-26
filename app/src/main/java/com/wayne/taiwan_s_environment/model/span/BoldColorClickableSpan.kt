package com.wayne.taiwan_s_environment.model.span

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.annotation.ColorInt

class BoldColorClickableSpan(@ColorInt val color: Int, private val id: Int, private val onClick: OnClickableSpanListener): ClickableSpan() {
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isFakeBoldText = true
        ds.isUnderlineText = true
        ds.color = color
    }

    override fun onClick(view: View) {
        onClick.onClick(id)
    }
}