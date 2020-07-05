package com.wayne.taiwan_s_environment.view.adapter.viewholder

import android.content.Context
import android.view.View
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

    private val textStatus: TextView = itemView.findViewById(R.id.text_status)

    fun setAQI(aqi: Home, listener: OnAQIClickListener) {
        textSiteName.text = aqi.siteName

        val aqiValue = ceil(aqi.aqi!!.toDouble()).toInt()

        textAQI.text = aqiValue.toString()
        textAQI.setTextColor(context.getAQIColor(aqiValue))
        textAgency.text = context.getString(R.string.environmental_protection_administration)

        textStatus.text = context.getString(R.string.brackets, aqi.status + if (!aqi.pollutant.isNullOrEmpty()) { " ${aqi.pollutant}" } else { "" })

        btnReadMore.setOnClickListener {
            listener.onClick(aqi)
        }
    }

    interface OnAQIClickListener {
        fun onClick(aqi: Home)
    }
}