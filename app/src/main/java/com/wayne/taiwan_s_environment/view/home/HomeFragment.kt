package com.wayne.taiwan_s_environment.view.home

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.model.exception.CountyNotFoundException
import com.wayne.taiwan_s_environment.model.exception.SameCountyException
import com.wayne.taiwan_s_environment.model.manager.LocationManager
import com.wayne.taiwan_s_environment.model.manager.LocationManagerListener
import com.wayne.taiwan_s_environment.view.adapter.HomeAdapter
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import com.wayne.taiwan_s_environment.view.dialog.selectcounty.OnCountySelectedListener
import com.wayne.taiwan_s_environment.view.dialog.selectcounty.SelectCountyDialog
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber


class HomeFragment : BaseFragment(R.layout.fragment_home), OnCountySelectedListener {

    companion object {
        const val PERMISSIONS_CODE = 1000
    }

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var locationManager: LocationManager
    private var locationManagerListener: LocationManagerListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManagerListener = object: LocationManagerListener {
            override fun onLocationUpdated(location: Location) {
                viewModel.getUVByLocation(location)
            }

            override fun updateFloatingLocationButton() {
                this@HomeFragment.updateFloatingLocationButton()
            }

        }
        locationManager = LocationManager(requireContext(), locationManagerListener)
        lifecycle.addObserver(locationManager)

        recycler_home.adapter = HomeAdapter(arrayListOf())

        viewModel.epaDataUpdate.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Empty -> {
                    swipe_refresh.isRefreshing = false
                }
                is ApiResult.Error -> {
                    it.throwable.printStackTrace()
                    showErrorMessage(getErrorMessage(it.throwable),
                        DialogInterface.OnClickListener { dialog, view ->
                            dialog.dismiss()
                        })
                }
            }
        })

        viewModel.epaList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Success -> {
                    (recycler_home.adapter as HomeAdapter).list = it.result
                    (recycler_home.adapter as HomeAdapter).notifyDataSetChanged()
                    if (viewModel.isPowerSaving()) {
                        locationManager.stopLocationUpdates()
                    }
                }

                is ApiResult.Error -> {
                    it.throwable.printStackTrace()
                    when (it.throwable) {
                        is CountyNotFoundException, is SameCountyException -> {
                            updateFloatingLocationButton()
                        }

                        else -> {
                            showErrorMessage(getErrorMessage(it.throwable),
                                DialogInterface.OnClickListener { dialog, view ->
                                    dialog.dismiss()
                                })
                        }
                    }
                }
            }
        })

        viewModel.county.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                text_guide.visibility = View.VISIBLE
            } else {
                text_guide.visibility = View.GONE
            }
            updateFloatingLocationButton()
        })

        btn_location.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                btn_location.setImageResource(R.drawable.ic_round_location_disabled_24)
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_CODE)
            } else if (viewModel.county.value == null) {
                btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
                showSelectCountyDialog()
            } else if (locationManager.isLocationUpdate()) {
                viewModel.setPowerSaving(true)
                locationManager.stopLocationUpdates()
            } else {
                viewModel.setPowerSaving(false)
                locationManager.startLocationUpdates()
            }
        }

        btn_location.setOnLongClickListener {
            showSelectCountyDialog()
            return@setOnLongClickListener true
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.updateEpaData()
        }

        viewModel.initData()
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

    override fun onDestroyView() {
        locationManager.listener = null
        lifecycle.removeObserver(locationManager)
        locationManagerListener = null
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    viewModel.setPowerSaving(false)
                    btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.oops)
                        .setMessage(R.string.go_settings_message)
                        .setNegativeButton(android.R.string.cancel, locationPermissionsDialogListener)
                        .setNeutralButton(R.string.select_region, locationPermissionsDialogListener)
                        .setPositiveButton(R.string.go_settings, locationPermissionsDialogListener)
                        .show()
                }
            }
        }
    }

    private fun updateFloatingLocationButton() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            btn_location.setImageResource(R.drawable.ic_round_location_disabled_24)
        } else if (viewModel.county.value == null) {
            btn_location.setImageResource(R.drawable.ic_round_location_unknown_24)
        } else if (locationManager.isLocationUpdate()) {
            btn_location.setImageResource(R.drawable.ic_round_my_location_24)
        } else {
            btn_location.setImageResource(R.drawable.ic_round_location_searching_24)
        }
    }

    private fun showSelectCountyDialog() {
        SelectCountyDialog.newInstance(R.array.taiwan_region).show(childFragmentManager, SelectCountyDialog::javaClass.name)
    }

    override fun onSelected(county: String) {
        Timber.e("selected county : $county")
        viewModel.setPowerSaving(true)
        locationManager.stopLocationUpdates()
        viewModel.getUVByCounty(county)
    }

    private val locationPermissionsDialogListener = DialogInterface.OnClickListener { dialogInterface, i ->
        when (i) {
            DialogInterface.BUTTON_NEGATIVE -> {
                // cancel
                dialogInterface.dismiss()
            }
            DialogInterface.BUTTON_NEUTRAL -> {
                // select region
                showSelectCountyDialog()
                dialogInterface.dismiss()
            }
            DialogInterface.BUTTON_POSITIVE -> {
                // go settings
                dialogInterface.dismiss()

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }
}