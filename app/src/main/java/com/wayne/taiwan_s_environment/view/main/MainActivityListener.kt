package com.wayne.taiwan_s_environment.view.main

import androidx.annotation.ColorRes

interface MainActivityListener {
    fun setBottomNavigationIsShow(isShow: Boolean)
    fun setToolBar(isShow: Boolean, @ColorRes backgroundColor: Int? = null)
}