package com.wayne.taiwan_s_environment.view.adapter.viewholder

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.format.DateUtils
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.model.span.BoldColorRelativeSizeSpan
import com.wayne.taiwan_s_environment.utils.getAQIColor
import com.wayne.taiwan_s_environment.utils.getUVColor
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class IntroViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val context: Context = itemView.context
    val textTitle: TextView = itemView.findViewById(R.id.text_title)
    val textContent: TextView = itemView.findViewById(R.id.text_content)
    val imgIcon: ImageView = itemView.findViewById(R.id.img_icon)
}