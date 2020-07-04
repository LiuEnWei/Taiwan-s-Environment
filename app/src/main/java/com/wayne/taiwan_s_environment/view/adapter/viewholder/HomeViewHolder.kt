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

class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    companion object {
        val DAY_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
        val TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.TAIWAN)
    }

    val context: Context = itemView.context
    private val imgProfile: ImageView = itemView.findViewById(R.id.img_profile)
    private val textTitle: TextView = itemView.findViewById(R.id.text_title)
    private val textTime: TextView = itemView.findViewById(R.id.text_time)
    private val textLocation: TextView = itemView.findViewById(R.id.text_location)
    private val textMessage: TextView = itemView.findViewById(R.id.text_message)
    private val btnMore: TextView = itemView.findViewById(R.id.btn_more)
    private val layoutAQIMore: TableLayout = itemView.findViewById(R.id.text_aqi_more)

    private val textPM2_5Avg: TextView = itemView.findViewById(R.id.text_pm_2_5_avg)
    private val textPM2_5: TextView = itemView.findViewById(R.id.text_pm_2_5)
    private val textPM_10Avg: TextView = itemView.findViewById(R.id.text_pm_10_avg)
    private val textPM_10: TextView = itemView.findViewById(R.id.text_pm_10)
    private val textO3_8hrAvg: TextView = itemView.findViewById(R.id.text_o3_8hr_avg)
    private val textO3: TextView = itemView.findViewById(R.id.text_o3)
    private val textC_O_8hrAvg: TextView = itemView.findViewById(R.id.text_c_o_8hr_avg)
    private val textC_O: TextView = itemView.findViewById(R.id.text_c_o)
    private val textS_O2Avg: TextView = itemView.findViewById(R.id.text_s_o_2_avg)
    private val textS_O2: TextView = itemView.findViewById(R.id.text_s_o_2)
    private val textN_O2: TextView = itemView.findViewById(R.id.text_n_o_2)

    fun setUV(data: Home) {
        imgProfile.setImageResource(R.drawable.ic_oval_black)
        setTitle(data.publishAgency, data.siteName)
        setUVMessage(data.uvi)
        setTime(data.time)
        textLocation.text = data.county
        btnMore.visibility = View.GONE
        layoutAQIMore.visibility = View.GONE
    }

    fun setAQI(data: Home) {
        imgProfile.setImageResource(R.drawable.ic_oval_black)
        setTime(data.time)
        textLocation.text = data.county
        setTitle(context.getString(R.string.environmental_protection_administration), data.siteName)
        textMessage.text = ""
        btnMore.setOnClickListener {
            if (btnMore.visibility == View.VISIBLE) {
                btnMore.visibility = View.GONE
                layoutAQIMore.visibility = View.VISIBLE
            }
        }
        btnMore.visibility = View.VISIBLE
        layoutAQIMore.visibility = View.GONE
        setAQIMessage(data.aqi, data.status, data.pollutant)
        textPM2_5Avg.text = data.PM2_5_AVG
        textPM2_5.text = data.PM2_5
        textPM_10Avg.text = data.PM10_AVG
        textPM_10.text = data.PM10
        textO3_8hrAvg.text = data.O3_8hr
        textO3.text = data.O3
        textC_O_8hrAvg.text = data.CO_8hr
        textC_O.text = data.CO
        textS_O2Avg.text = data.SO2_AVG
        textS_O2.text = data.SO2
        textN_O2.text = data.NO2
    }

    private fun setTitle(publishAgency: String?, siteName: String?) {
        if (publishAgency == null || siteName == null) return
        val title = context.getString(R.string.item_more_title, publishAgency, siteName)
        val titleSpannable = SpannableString(title)
        val agencyStart = title.indexOf(publishAgency)
        titleSpannable.setSpan(StyleSpan(Typeface.BOLD), agencyStart, agencyStart + publishAgency.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        val siteNameStart = title.indexOf(siteName)
        titleSpannable.setSpan(StyleSpan(Typeface.BOLD), siteNameStart, siteNameStart + siteName.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        textTitle.text = titleSpannable
    }

    private fun setTime(publishTime: Long?) {
        if (publishTime == null) return
        if (DateUtils.isToday(publishTime)) {
            textTime.text = TIME_FORMAT.format(publishTime)
        } else {
            textTime.text = DAY_FORMAT.format(publishTime)
        }
    }

    private fun setUVMessage(uvi: String?) {
        if (uvi == null) return
        try {
            val uv = ceil(uvi.toDouble()).toInt()
//            val uv = uvi.toFloat().roundToInt()

            val messageBuilder = SpannableStringBuilder()
            messageBuilder.appendln(context.getString(R.string.uv_index, uvi))
            messageBuilder.appendln()
            messageBuilder.appendln()

            val uviStart = messageBuilder.indexOf(uvi)
            messageBuilder.setSpan(BoldColorRelativeSizeSpan(context.getUVColor(uv),2f), uviStart, uviStart + uvi.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

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
                    messageBuilder.appendln(uvIndexLevel)
                    messageBuilder.appendln(context.getString(R.string.sunburn_time, 30))
                    messageBuilder.append(context.getString(R.string.pay_attention_to_sun_protection))
                }

                uv in 8..10 -> {
                    uvIndexLevel = "#${context.getString(R.string.uv_index_excess)}"
                    messageBuilder.appendln(uvIndexLevel)
                    messageBuilder.appendln(context.getString(R.string.sunburn_time, 20))
                    messageBuilder.append(context.getString(R.string.pay_attention_to_sun_protection))
                }

                uv >= 11 -> {
                    uvIndexLevel = "#${context.getString(R.string.uv_index_dangerous)}"
                    messageBuilder.appendln(uvIndexLevel)
                    messageBuilder.appendln(context.getString(R.string.sunburn_time, 15))
                    messageBuilder.append(context.getString(R.string.pay_attention_to_sun_protection))
                }
            }
            uvIndexLevel?.let {
                val levelStart = messageBuilder.indexOf(it)
                messageBuilder.setSpan(BoldColorRelativeSizeSpan(context.resources.getColor(R.color.colorLightBlue700)), levelStart, messageBuilder.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            }

            textMessage.text = messageBuilder
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setAQIMessage(aqi: String?, status: String?, pollutant: String?) {
        if (aqi == null) return
        try {
            val aqiColor = context.getAQIColor(ceil(aqi.toDouble()).toInt())
            val messageBuilder = SpannableStringBuilder()
            messageBuilder.appendln(context.getString(R.string.aqi, aqi))
            messageBuilder.appendln()
            messageBuilder.appendln()

            val aqiStart = messageBuilder.indexOf(aqi)
            messageBuilder.setSpan(
                BoldColorRelativeSizeSpan(aqiColor, 2f),
                aqiStart,
                aqiStart + aqi.length,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )

            if (!status.isNullOrEmpty()) {
                context.getString(R.string.aqi_status, status).let {
                    messageBuilder.appendln(it)
                    val start = messageBuilder.indexOf(it)
                    messageBuilder.setSpan(
                        BoldColorRelativeSizeSpan(context.resources.getColor(R.color.colorLightBlue700)),
                        start,
                        start + it.length,
                        Spanned.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
            }

            if (!pollutant.isNullOrEmpty()) {
                context.getString(R.string.aqi_pollutant, pollutant).let {
                    messageBuilder.appendln(it)
                    val start = messageBuilder.indexOf(it)
                    messageBuilder.setSpan(
                        BoldColorRelativeSizeSpan(context.resources.getColor(R.color.colorLightBlue700)),
                        start,
                        start + it.length,
                        Spanned.SPAN_INCLUSIVE_INCLUSIVE
                    )
                }
            }
            textMessage.text = messageBuilder
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}