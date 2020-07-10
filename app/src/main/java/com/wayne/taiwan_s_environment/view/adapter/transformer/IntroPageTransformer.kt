package com.wayne.taiwan_s_environment.view.adapter.transformer

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.wayne.taiwan_s_environment.R

class IntroPageTransformer: ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val width = page.width

        val img = page.findViewById<ImageView>(R.id.img_icon)
        val drawable = img.drawable
        val title = page.findViewById<TextView>(R.id.text_title)
        val content = page.findViewById<TextView>(R.id.text_content)
        title.translationX = position * width * 0.4f
        content.translationX = position * width * 0.2f
        when {
            position < -1 -> {
//                [-Infinity,-1]
                // This page is way off-screen to the left
                page.alpha = 0f
            }

            position <= 0 -> {
                // [-1, 0]
                page.alpha = 1f + position
                // Use the default slide transition when moving to the left page
                if (position == 0f) {
                    when {
                        drawable is AnimatedVectorDrawable && !drawable.isRunning-> {
                            drawable.start()
                        }
                    }
                }
            }

            position <= 1 -> {
                // [0, 1]
                page.alpha = 1f - position
            }

            else -> {
                // [1, +Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0f
            }
        }
    }
}