package com.wayne.taiwan_s_environment.view.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.main.MainActivity
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

    fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun showErrorMessage(message: String?, okClickListener: DialogInterface.OnClickListener?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(R.string.oops)
        dialog.setMessage(message)
        dialog.setCancelable(false)
        dialog.setPositiveButton(android.R.string.ok, okClickListener)
        return dialog.show()
    }

    fun getErrorMessage(throwable: Throwable): String? {
        return if (isConnected()) {
            throwable.message
        } else {
            getString(R.string.network_error_message)
        }
    }

    fun isConnected(): Boolean {
        return (requireActivity() as MainActivity).isConnected()
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