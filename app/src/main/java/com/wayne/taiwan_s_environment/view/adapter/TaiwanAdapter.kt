package com.wayne.taiwan_s_environment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.enum.EpaDataType
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.view.adapter.viewholder.TaiwanViewHolder

class TaiwanAdapter(var list: List<Home>): RecyclerView.Adapter<TaiwanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaiwanViewHolder {
        return TaiwanViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_taiwan, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaiwanViewHolder, position: Int) {
        val data = list[position]
        when (data.epaDataType) {
            EpaDataType.UV.value -> {
                holder.setUV(data)
            }
            EpaDataType.AQI.value -> {
            }
        }
    }
}