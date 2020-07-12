package com.wayne.taiwan_s_environment.model.manager

import android.location.Location

interface LocationManagerListener {
    fun onLocationUpdated(location: Location)
    fun updateFloatingLocationButton()
}