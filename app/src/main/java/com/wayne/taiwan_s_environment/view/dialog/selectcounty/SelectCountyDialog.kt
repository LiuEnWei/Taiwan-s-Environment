package com.wayne.taiwan_s_environment.view.dialog.selectcounty

import android.os.Bundle
import android.view.View
import androidx.annotation.ArrayRes
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.adapter.SelectCountyAdapter
import com.wayne.taiwan_s_environment.view.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_select_county.*

class SelectCountyDialog: BaseDialogFragment(R.layout.dialog_select_county), OnCountySelectedListener {

    var onCountySelectedListener: OnCountySelectedListener? = null

    companion object {
        private const val KEY_ARRAY_ID = "KEY_ARRAY_ID"
        fun newInstance(@ArrayRes arrayId: Int): SelectCountyDialog {
            return SelectCountyDialog().apply {
                arguments = Bundle().also {
                    it.putInt(KEY_ARRAY_ID, arrayId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            onCountySelectedListener = parentFragment as OnCountySelectedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling fragment must implement Callback interface")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayId = arguments?.getInt(KEY_ARRAY_ID)
        arrayId?.let {
            recycler_county.adapter = SelectCountyAdapter(resources.getStringArray(it), this)
        }

        parent_layout.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onSelected(county: String) {
        onCountySelectedListener?.onSelected(county)
        this.dismiss()
    }
}