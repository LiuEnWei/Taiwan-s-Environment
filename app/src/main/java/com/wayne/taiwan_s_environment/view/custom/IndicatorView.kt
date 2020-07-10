package com.wayne.taiwan_s_environment.view.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.Px
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.utils.toDp
import kotlinx.android.synthetic.main.fragment_intro.*
import timber.log.Timber

class IndicatorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val dotColor: Int
    private val indicatorColor: Int
    private val dotSpacing: Int
    private val dotSize: Int
    private val dotStroke: Int
    private val dotCornerRadius: Int
    private val dotDrawable: GradientDrawable
    private val indicatorDrawable: GradientDrawable
    private var indicatorView: View? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)
        dotColor = typedArray.getColor(R.styleable.IndicatorView_dotColor, Color.BLACK)
        indicatorColor = typedArray.getColor(R.styleable.IndicatorView_indicatorColor, Color.BLACK)
        dotSpacing = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_dotSpacing, 0)
        dotSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_dotSize, 0)
        dotStroke = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_dotStroke, 1)
        dotCornerRadius = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_cornerRadius, 90)

        dotDrawable = GradientDrawable()
        dotDrawable.shape = GradientDrawable.RECTANGLE
        dotDrawable.cornerRadius = dotCornerRadius.toFloat()
        dotDrawable.setStroke(dotStroke, dotColor)

        indicatorDrawable = GradientDrawable()
        indicatorDrawable.shape = GradientDrawable.RECTANGLE
        indicatorDrawable.cornerRadius = dotCornerRadius.toFloat()
        indicatorDrawable.setColor(indicatorColor)

        typedArray.recycle()
    }

    private var adapterDataObserver: RecyclerView.AdapterDataObserver? = null
    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var viewPager2: ViewPager2? = null

    fun setViewPager2(viewPager2: ViewPager2) {
        val adapter = viewPager2.adapter ?: return
        this.viewPager2 = viewPager2
        adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                initIndicatorView(adapter.itemCount, viewPager2.currentItem)
            }
        }
        adapter.registerAdapterDataObserver(adapterDataObserver!!)


        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            val spacing = dotSpacing + dotSize
            override fun onPageScrolled(position: Int, positionOffset: Float, @Px positionOffsetPixels: Int) {
                if (positionOffset <= 0.5) {
                    indicatorView?.setLayoutParamsWidth((dotSize + spacing * positionOffset / 0.5f).toInt())
                    indicatorView?.translationX = (position * spacing).toFloat()
                } else {
                    indicatorView?.setLayoutParamsWidth((dotSize + spacing * (1 - positionOffset) / 0.5f).toInt())
                    indicatorView?.translationX = (positionOffset / 0.5f - 1) * spacing + position * spacing
                }
            }
        }

        viewPager2.registerOnPageChangeCallback(onPageChangeCallback!!)
        initIndicatorView(adapter.itemCount, viewPager2.currentItem)
    }

    fun initIndicatorView(itemCount: Int, currentItem: Int) {
        if (itemCount > 0) {

            for (position in 0 until itemCount) {
                addView(getDotView(position))
            }

            indicatorView = getIndicatorView(currentItem)
            addView(indicatorView)
        }
    }

    private fun getDotView(position: Int): View {
        val view = View(context)
        val layoutParams = LayoutParams(dotSize, dotSize)
        layoutParams.marginStart = (dotSize + dotSpacing) * position
        view.layoutParams = layoutParams
        view.background = dotDrawable
        return view
    }

    private fun getIndicatorView(currentItem: Int): View {
        val view = View(context)
        view.id = View.generateViewId()
        view.layoutParams = LayoutParams(dotSize, dotSize)
        view.background = indicatorDrawable
        view.translationX = ((dotSpacing + dotSize) * currentItem).toFloat()
        return view
    }

    fun View.setLayoutParamsWidth(width: Int) {
        val layoutParams = this.layoutParams
        layoutParams.width = width
        this.layoutParams = layoutParams
    }

    private fun clearAllObserver() {
        adapterDataObserver?.let {
            this.viewPager2?.adapter?.unregisterAdapterDataObserver(it)
        }
        onPageChangeCallback?.let {
            this.viewPager2?.unregisterOnPageChangeCallback(it)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearAllObserver()
    }
}