package com.wayne.taiwan_s_environment.view.references

import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment

class ReferencesFragment: BaseFragment(R.layout.fragment_references) {

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }
}