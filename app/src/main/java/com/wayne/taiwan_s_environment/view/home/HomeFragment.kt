package com.wayne.taiwan_s_environment.view.home

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.Html
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.exception.CountyNotFoundException
import com.wayne.taiwan_s_environment.view.adapter.HomeAdapter
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    companion object {
        const val PERMISSIONS_CODE = 1000
    }

    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var isLocationUpdates: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLocation()

        viewModel.openUvUpdate.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Empty -> {
                    Timber.e("uv update success")
                    swipe_refresh.isRefreshing = false
                }
                is ApiResult.Error -> {
                    Timber.e("uv update error ${it.throwable}")
                    //TODO show error dialog
                }
            }
        })

        viewModel.uvList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Empty -> {
                    Timber.e("uv list empty")
                }

                is ApiResult.Success -> {
                    Timber.e("uv list success")
                    recycler_home.adapter = HomeAdapter(it.result)
                }

                is ApiResult.Error -> {
                    Timber.e("uv list error")
                    Timber.e("${it.throwable}")
                    when (it.throwable) {
                        is CountyNotFoundException -> {
                            Timber.e("CountyNotFoundException")
                            btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
                            //TODO show dialog to set county
                        }

                        else -> {
                            //TODO show error dialog
                        }
                    }
                }
            }
        })

        viewModel.county.observe(viewLifecycleOwner, Observer {
            Timber.e("county : $it")
            if (it.isNullOrEmpty()) {
                btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
            } else {
                btn_location.setImageResource(R.drawable.ic_round_my_location_24)
            }
        })

        btn_location.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                btn_location.setImageResource(R.drawable.ic_round_location_disabled_24)
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_CODE)
            } else if (viewModel.county.value == null) {
                btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
                //TODO show dialog to set county
            } else if (isLocationUpdates) {
                stopLocationUpdates()
            } else {
                startLocationUpdates()
            }
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.getUVOpenData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isLocationUpdates) startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
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

    private fun initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    // Update UI with location data
                    // ...
                    Timber.e("location : $location")
                    viewModel.getUVByLocation(location)
                }
            }
        }

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            Timber.e("locationSettingsResponse success")
        }

        task.addOnFailureListener { exception ->
            Timber.e("locationSettingsResponse failure")
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
//                    exception.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                Timber.e("lastLocation : $location")
                location?.let {
                    viewModel.getUVByLocation(it)
                }
            }
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            isLocationUpdates = true
        } else {
            btn_location.setImageResource(R.drawable.ic_round_location_disabled_24)
        }
    }

    private fun stopLocationUpdates() {
        btn_location.setImageResource(R.drawable.ic_round_location_searching_24)
        fusedLocationClient.removeLocationUpdates(locationCallback)
        isLocationUpdates = false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_CODE -> {
                btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
            }
        }
    }
}