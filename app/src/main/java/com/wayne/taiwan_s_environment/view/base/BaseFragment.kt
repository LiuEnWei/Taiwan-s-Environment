package com.wayne.taiwan_s_environment.view.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.main.MainActivityListener

abstract class BaseFragment(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {

    var onMainActivityListener: MainActivityListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onMainActivityListener = context as MainActivityListener
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onMainActivityListener?.setBottomNavigationIsShow(isBottomNavigationShow())
        onMainActivityListener?.setToolBar(isToolBarShow() ,getToolBarColor())
        initStatusBar()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initStatusBar() {
        val statusBarColor = getColor(requireContext(), getStatusBarColor())
        requireActivity().window.statusBarColor = statusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val uiVisibility = requireActivity().window.decorView.systemUiVisibility
            requireActivity().window.decorView.systemUiVisibility = if (ColorUtils.calculateLuminance(statusBarColor) >= 0.5f) {
                // is light
                uiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                // is dark
                uiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    open fun isBottomNavigationShow(): Boolean {
        return false
    }

    open fun isToolBarShow(): Boolean {
        return false
    }

    open fun getToolBarColor(): Int {
        return R.color.colorWhite
    }

    open fun getStatusBarColor(): Int {
        return getToolBarColor()
    }
}