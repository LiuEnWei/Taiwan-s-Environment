package com.wayne.taiwan_s_environment.view.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.exception.CountyNotFoundException
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

// TODO
class HomeFragment : BaseFragment(R.layout.fragment_home), LocationListener {

    companion object {
        const val PERMISSIONS_CODE = 1000
    }

    private val viewModel by viewModel<HomeViewModel>()
    private var locationManager: LocationManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_CODE)

        viewModel.uvList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Empty -> {
                    Timber.e("uv list empty")
                }

                is ApiResult.Success -> {
                    Timber.e("uv list success")
                }

                is ApiResult.Error -> {
                    Timber.e("uv list error")
                    Timber.e("${it.throwable}")
                    when (it.throwable) {
                        is CountyNotFoundException -> {
                            Timber.e("CountyNotFoundException")
                        }
                    }
                }
            }
        })

        viewModel.county.observe(viewLifecycleOwner, Observer {
            text.text = it
        })
    }

    override fun onResume() {
        super.onResume()
        initLocationManger()
    }

    override fun onPause() {
        super.onPause()
        locationManager?.removeUpdates(this)
    }

    override fun isBottomNavigationShow(): Boolean {
        return true
    }

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }

    private fun initLocationManger() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager?.let {
                var location: Location? = null
                if (it.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Timber.e("GPS_PROVIDER")
                    it.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 100F, this)
                    location = it.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                } else if (it.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Timber.e("NETWORK_PROVIDER")
                    location = it.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    it.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 100F, this)
                }
                if (location != null) viewModel.getUVByLocation(location)
            }
        }

        Timber.e("initLocationManger : $locationManager")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_CODE -> {

            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        Timber.e("onLocationChanged")
        location?.let {
            viewModel.getUVByLocation(it)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}