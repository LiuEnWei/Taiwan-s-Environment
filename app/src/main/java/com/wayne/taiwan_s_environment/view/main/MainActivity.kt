package com.wayne.taiwan_s_environment.view.main

import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main), MainActivityListener {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appBarConfig = AppBarConfiguration(setOf(R.id.splashFragment, R.id.introFragment, R.id.homeFragment, R.id.taiwanFragment,R.id.moreFragment))
        toolbar.setupWithNavController(findNavController(), appBarConfig)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(findNavController(), appBarConfig)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

    fun isConnected(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
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