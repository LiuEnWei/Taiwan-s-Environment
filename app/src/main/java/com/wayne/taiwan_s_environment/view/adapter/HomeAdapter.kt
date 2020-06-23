package com.wayne.taiwan_s_environment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.vo.UV
import com.wayne.taiwan_s_environment.view.adapter.viewholder.HomeViewHolder

class HomeAdapter(var list: List<UV>): RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val uv = list[position]
        holder.imgProfile.setImageResource(R.drawable.ic_oval_black)
        holder.setTitle(uv.publishAgency, uv.siteName)
        holder.setTime(uv.time)
        holder.setMessage(uv.uvi)
        holder.textLocation.text = uv.county
    }
}