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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.span.BoldColorSpan
import com.wayne.taiwan_s_environment.utils.getUVColor
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    companion object {
        val DAY_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
        val TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.TAIWAN)
    }

    val context: Context = itemView.context
    val imgProfile: ImageView = itemView.findViewById(R.id.img_profile)
    val textTitle: TextView = itemView.findViewById(R.id.text_title)
    val textTime: TextView = itemView.findViewById(R.id.text_time)
    val textLocation: TextView = itemView.findViewById(R.id.text_location)
    val textMessage: TextView = itemView.findViewById(R.id.text_message)

    fun setTitle(publishAgency: String?, siteName: String?) {
        if (publishAgency == null || siteName == null) return
        val title = context.getString(R.string.item_more_title, publishAgency, siteName)
        val titleSpannable = SpannableString(title)
        val agencyStart = title.indexOf(publishAgency)
        titleSpannable.setSpan(StyleSpan(Typeface.BOLD), agencyStart, agencyStart + publishAgency.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        val siteNameStart = title.indexOf(siteName)
        titleSpannable.setSpan(StyleSpan(Typeface.BOLD), siteNameStart, siteNameStart + siteName.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        textTitle.text = titleSpannable
    }

    fun setTime(publishTime: Long?) {
        if (publishTime == null) return
        if (DateUtils.isToday(publishTime)) {
            textTime.text = TIME_FORMAT.format(publishTime)
        } else {
            textTime.text = DAY_FORMAT.format(publishTime)
        }
    }

    fun setMessage(uvi: String?) {
        if (uvi == null) return
        try {
            val uv = ceil(uvi.toDouble()).toInt()
//            val uv = uvi.toFloat().roundToInt()

            val messageBuilder = SpannableStringBuilder()
            messageBuilder.append(context.getString(R.string.uv_index, uvi))
            messageBuilder.append("\n")
            messageBuilder.append("\n")
            messageBuilder.append("\n")

            val uviStart = messageBuilder.indexOf(uvi)
            messageBuilder.setSpan(BoldColorSpan(context.getUVColor(uv),2f), uviStart, uviStart + uvi.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            var uvIndexLevel: String? = null
            when {
                uv in 0..2 -> {
                    uvIndexLevel = "#${context.getString(R.string.uv_index_low)}"
                    messageBuilder.append(uvIndexLevel)
                }

                uv in 3..5 -> {
                    uvIndexLevel = "#${context.getString(R.string.uv_index_middleweight)}"
                    messageBuilder.append(uvIndexLevel)
                }

                uv in 6..7 -> {
                    uvIndexLevel = "#${context.getString(R.string.uv_index_high)}"
                    messageBuilder.append(uvIndexLevel)
                    messageBuilder.append(context.getString(R.string.sunburn_time, 30))
                    messageBuilder.append(context.getString(R.string.pay_attention_to_sun_protection))
                }

                uv in 8..10 -> {
                    uvIndexLevel = "#${context.getString(R.string.uv_index_excess)}"
                    messageBuilder.append(uvIndexLevel)
                    messageBuilder.append(context.getString(R.string.sunburn_time, 20))
                    messageBuilder.append(context.getString(R.string.pay_attention_to_sun_protection))
                }

                uv >= 11 -> {
                    uvIndexLevel = "#${context.getString(R.string.uv_index_dangerous)}"
                    messageBuilder.append(uvIndexLevel)
                    messageBuilder.append(context.getString(R.string.sunburn_time, 15))
                    messageBuilder.append(context.getString(R.string.pay_attention_to_sun_protection))
                }
            }
            uvIndexLevel?.let {
                val levelStart = messageBuilder.indexOf(it)
                messageBuilder.setSpan(BoldColorSpan(context.resources.getColor(R.color.colorLightBlue700)), levelStart, messageBuilder.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            }

            textMessage.text = messageBuilder
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}