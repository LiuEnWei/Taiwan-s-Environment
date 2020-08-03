package com.wayne.taiwan_s_environment.view.adapter.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.utils.getAQIColor
import kotlin.math.ceil

class TaiwanAQIViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val context: Context = itemView.context
    private val btnReadMore: View = itemView.findViewById(R.id.btn_read_more)
    private val textSiteName: TextView = itemView.findViewById(R.id.text_site_name)
    private val textAQI: TextView = itemView.findViewById(R.id.text_aqi)
    private val textAgency: TextView = itemView.findViewById(R.id.text_agency)
    private val imgChevronRight: ImageView = itemView.findViewById(R.id.img_chevron_right)

    private val textStatus: TextView = itemView.findViewById(R.id.text_status)

    companion object {
        const val AQI_EMPTY = -1
    }

    fun setAQI(aqi: Home, listener: OnAQIClickListener) {
        textSiteName.text = aqi.siteName

        val aqiValue = try {
            ceil(aqi.aqi!!.toDouble()).toInt()
        } catch (e: Exception) {
            AQI_EMPTY
        }

        textAQI.text = if (aqiValue == AQI_EMPTY) {
            btnReadMore.setOnClickListener (null)
            imgChevronRight.visibility = View.INVISIBLE
            "-"
        } else {
            btnReadMore.setOnClickListener {
                listener.onClick(aqi)
            }
            imgChevronRight.visibility = View.VISIBLE
            aqiValue.toString()
        }
        textAQI.setTextColor(context.getAQIColor(aqiValue))

        textAgency.text = context.getString(R.string.environmental_protection_administration)

        textStatus.text = context.getString(R.string.brackets, aqi.status + if (!aqi.pollutant.isNullOrEmpty()) { " ${aqi.pollutant}" } else { "" })
    }

    interface OnAQIClickListener {
        fun onClick(aqi: Home)
    }
}