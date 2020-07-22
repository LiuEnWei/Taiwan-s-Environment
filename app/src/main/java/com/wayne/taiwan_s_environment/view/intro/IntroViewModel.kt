package com.wayne.taiwan_s_environment.view.intro

import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import org.koin.core.inject

class IntroViewModel : BaseViewModel() {
    private val repository: Repository by inject()

    fun isFirstStartApp(): Boolean {
        return repository.isFirstStartApp()
    }

    fun introFinish() {
        repository.setFirstStartApp(false)
    }
}