package com.wayne.taiwan_s_environment.view.adapter.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R

class IntroViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val context: Context = itemView.context
    val textTitle: TextView = itemView.findViewById(R.id.text_title)
    val textContent: TextView = itemView.findViewById(R.id.text_content)
    val imgIcon: ImageView = itemView.findViewById(R.id.img_icon)
}