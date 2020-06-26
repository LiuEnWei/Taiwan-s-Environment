package com.wayne.taiwan_s_environment.view.adapter.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.vo.UV
import com.wayne.taiwan_s_environment.utils.getUVColor
import kotlin.math.ceil

class TaiwanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val context: Context = itemView.context
    val textSiteName: TextView = itemView.findViewById(R.id.text_site_name)
    val textUV: TextView = itemView.findViewById(R.id.text_uv)
    val textAgency: TextView = itemView.findViewById(R.id.text_agency)

    fun setUV(uv: UV) {
        textSiteName.text = uv.siteName

        val uvi = ceil(uv.uvi.toDouble()).toInt()

        textUV.text = uvi.toString()
        textUV.setTextColor(context.getUVColor(uvi))
        textAgency.text = uv.publishAgency
    }
}