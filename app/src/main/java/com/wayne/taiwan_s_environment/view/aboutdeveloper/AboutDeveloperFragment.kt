package com.wayne.taiwan_s_environment.view.aboutdeveloper

import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment

class AboutDeveloperFragment: BaseFragment(R.layout.fragment_about_developer) {

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }
}