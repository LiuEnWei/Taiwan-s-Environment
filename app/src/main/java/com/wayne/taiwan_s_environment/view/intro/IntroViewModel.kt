package com.wayne.taiwan_s_environment.view.intro

import com.wayne.taiwan_s_environment.model.pref.Pref
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import org.koin.core.inject

class IntroViewModel : BaseViewModel() {
    private val pref: Pref by inject()

    fun isFirstStartApp(): Boolean {
        return pref.isFirstStartApp
    }

    fun introFinish() {
        pref.isFirstStartApp = false
    }
}