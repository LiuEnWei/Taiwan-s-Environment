package com.wayne.taiwan_s_environment.view.privacypolicy

import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment

class PrivacyPolicyFragment: BaseFragment(R.layout.fragment_privacy_policy) {

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }
}