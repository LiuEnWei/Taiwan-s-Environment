package com.wayne.taiwan_s_environment.view.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.main.MainActivity
import com.wayne.taiwan_s_environment.view.main.MainActivityListener
import timber.log.Timber

abstract class BaseFragment: Fragment() {

    var onMainActivityListener: MainActivityListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onMainActivityListener = context as MainActivityListener
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMainActivityListener?.setBottomNavigationIsShow(isBottomNavigationShow())
    }

    open fun isBottomNavigationShow(): Boolean {
        return false
    }

    abstract fun getLayoutId(): Int

    fun showErrorMessageDialog() {
        val errorDialog = AlertDialog.Builder(requireActivity())
        errorDialog.setTitle("Error")
        errorDialog.setMessage("Message")
        errorDialog.setCancelable(false)
        errorDialog.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, id: Int) {

                }
            })
        errorDialog.setNegativeButton("Cancel",  object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, id: Int) {
                }
            })
        errorDialog.show()
    }
}