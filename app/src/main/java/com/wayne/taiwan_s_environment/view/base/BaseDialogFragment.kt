package com.wayne.taiwan_s_environment.view.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment(@LayoutRes val contentLayoutId: Int): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, 0)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentLayoutId, container, false)
    }

    override fun onStart() {
        super.onStart()
        if (isFull()) {
            dialog!!.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        }
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    open fun isFull(): Boolean {
        return false
    }
}