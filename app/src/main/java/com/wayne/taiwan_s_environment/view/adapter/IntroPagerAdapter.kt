package com.wayne.taiwan_s_environment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.adapter.viewholder.IntroViewHolder

class IntroPagerAdapter: RecyclerView.Adapter<IntroViewHolder>() {

    data class Intro(@StringRes val title: Int, @StringRes val content: Int, @DrawableRes val icon: Int?)

    private val introArray = arrayOf(
        Intro(R.string.intro_title_first, R.string.intro_content_first, R.drawable.animated_android),
        Intro(R.string.intro_title_second, R.string.intro_content_second, R.drawable.animated_map),
        Intro(R.string.intro_title_third, R.string.intro_content_third, R.drawable.animated_share))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        return IntroViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_intro, parent, false))
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.imgIcon.tag = position
        val data = introArray[position]
        when {
            data.icon != null -> holder.imgIcon.setImageResource(data.icon)
            else -> holder.imgIcon.setImageDrawable(null)
        }
        holder.textTitle.setText(data.title)
        holder.textContent.setText(data.content)
    }

    override fun getItemCount(): Int {
        return introArray.size
    }
}