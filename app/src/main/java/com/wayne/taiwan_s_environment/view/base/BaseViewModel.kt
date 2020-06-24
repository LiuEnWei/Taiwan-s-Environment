package com.wayne.taiwan_s_environment.view.base

import androidx.lifecycle.ViewModel
import com.wayne.taiwan_s_environment.MyApplication
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {
    val context = MyApplication.applicationContext()
}