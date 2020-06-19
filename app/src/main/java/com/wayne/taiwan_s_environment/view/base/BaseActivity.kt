package com.wayne.taiwan_s_environment.view.base

import android.content.res.Configuration
import android.content.res.Resources
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f) {
            resources
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        if (res.configuration.fontScale != 1f) {
            res.configuration.setToDefaults()
            val context = baseContext.createConfigurationContext(res.configuration)
            return context.resources
        }
        return super.getResources()
    }
}