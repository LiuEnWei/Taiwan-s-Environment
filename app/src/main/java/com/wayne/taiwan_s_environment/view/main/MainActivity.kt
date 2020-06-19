package com.wayne.taiwan_s_environment.view.main

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity(R.layout.activity_main), MainActivityListener {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appBarConfig = AppBarConfiguration(setOf(R.id.splashFragment, R.id.introFragment, R.id.homeFragment, R.id.taiwanFragment,R.id.moreFragment))
        toolbar.setupWithNavController(findNavController(), appBarConfig)
        setSupportActionBar(toolbar)

        bottom_navigation.setOnNavigationItemSelectedListener {
            if (it.itemId != bottom_navigation.selectedItemId) {
                when (it.itemId) {
                    R.id.btn_home -> {
                        findNavController().navigate(R.id.action_bottom_nav_to_homeFragment)
                    }
                    R.id.btn_taiwan_region -> {
                        findNavController().navigate(R.id.action_bottom_nav_to_taiwanFragment)
                    }
                    R.id.btn_more -> {
                        findNavController().navigate(R.id.action_bottom_nav_to_moreFragment)
                    }
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private fun findNavController(): NavController {
        return findNavController(R.id.nav_host_fragment)
    }

    override fun setBottomNavigationIsShow(isShow: Boolean) {
        if (isShow) {
            bottom_navigation.visibility = View.VISIBLE
        } else {
            bottom_navigation.visibility = View.GONE
        }
    }

    override fun setToolBar(isShow: Boolean, backgroundColor: Int?) {
        if (isShow) {
            toolbar.visibility = View.VISIBLE
        } else {
            toolbar.visibility = View.GONE
        }
        backgroundColor?.let {
            toolbar.backgroundTintList = ColorStateList.valueOf(resources.getColor(backgroundColor))
        }
    }
}