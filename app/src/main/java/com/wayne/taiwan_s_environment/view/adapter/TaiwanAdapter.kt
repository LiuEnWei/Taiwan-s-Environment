package com.wayne.taiwan_s_environment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.enum.EpaDataType
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.view.adapter.viewholder.TaiwanAQIViewHolder
import com.wayne.taiwan_s_environment.view.adapter.viewholder.TaiwanUVViewHolder

class TaiwanAdapter(var list: List<Home>, private val onAQIClickListener: TaiwanAQIViewHolder.OnAQIClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EpaDataType.UV.value -> TaiwanUVViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_taiwan_uv, parent, false))
            else -> TaiwanAQIViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_taiwan_aqi, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        when (holder) {
            is TaiwanUVViewHolder -> {
                holder.setUV(data)
            }
            is TaiwanAQIViewHolder -> {
                holder.setAQI(data, onAQIClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].epaDataType
    }
}