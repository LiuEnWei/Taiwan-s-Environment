package com.wayne.taiwan_s_environment.view.imageoriginal

import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment

class ImageOriginalFragment: BaseFragment(R.layout.fragment_image_original) {

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }
}