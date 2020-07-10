package com.wayne.taiwan_s_environment.view.adapter.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.utils.getUVColor
import kotlin.math.ceil

class TaiwanUVViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val context: Context = itemView.context
    private val textSiteName: TextView = itemView.findViewById(R.id.text_site_name)
    private val textUV: TextView = itemView.findViewById(R.id.text_uv)
    private val textAgency: TextView = itemView.findViewById(R.id.text_agency)

    fun setUV(uv: Home) {
        textSiteName.text = uv.siteName

        val uvi = ceil(uv.uvi!!.toDouble()).toInt()

        textUV.text = uvi.toString()
        textUV.setTextColor(context.getUVColor(uvi))
        textAgency.text = uv.publishAgency
    }
}