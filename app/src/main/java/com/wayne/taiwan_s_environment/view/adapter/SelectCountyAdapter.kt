package com.wayne.taiwan_s_environment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.adapter.viewholder.SelectCountyViewHolder
import com.wayne.taiwan_s_environment.view.dialog.selectcounty.OnCountySelectedListener

class SelectCountyAdapter(var list: Array<String>,private val listener: OnCountySelectedListener?): RecyclerView.Adapter<SelectCountyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCountyViewHolder {
        return SelectCountyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selected_county, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SelectCountyViewHolder, position: Int) {
        holder.textCounty.text = list[position]
        holder.textCounty.setOnClickListener {
            listener?.onSelected(list[position])
        }
    }
}