package com.wayne.taiwan_s_environment.view.aboutdeveloper

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about_developer.*

class AboutDeveloperFragment: BaseFragment(R.layout.fragment_about_developer) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_source_code.setOnClickListener {
            openUrl(Constant.LINK_GITHUB_SOURCES_CODE)
        }
    }

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }
}