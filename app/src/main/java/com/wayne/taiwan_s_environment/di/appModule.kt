package com.wayne.taiwan_s_environment.di

import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.model.pref.Pref
import org.koin.dsl.module

val appModule = module {
    single { createPref() }
}

fun createPref() : Pref {
    return Pref(Constant.PREFS_NAME)
}