package com.wayne.taiwan_s_environment.view.references

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_references.*

class ReferencesFragment: BaseFragment(R.layout.fragment_references) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_taiwan_epa.setOnClickListener {
            openUrl(Constant.LINK_TAIWAN_EPA_OPEN_DATA)
        }
    }

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }
}