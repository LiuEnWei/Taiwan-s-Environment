package com.wayne.taiwan_s_environment.view.main

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wayne.taiwan_s_environment.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainActivityListener {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setBottomNavigationIsShow(isShow: Boolean) {
        if (isShow) {
            bottom_navigation.visibility = View.VISIBLE
        } else {
            bottom_navigation.visibility = View.GONE
        }
    }

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