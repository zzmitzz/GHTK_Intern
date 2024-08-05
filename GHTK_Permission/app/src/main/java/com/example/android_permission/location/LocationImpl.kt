package com.example.android_permission.location

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

/**
 * The implementation of [Location] class, get location from both GPS and Network
 *
 * @param locationManager: [LocationManager]
 */
@SuppressLint("MissingPermission")
class LocationImpl(
    private val locationManager: LocationManager,
) {
    private var locationGPS: Location? = null
    private var locationNetwork: Location? = null

    private var locationGPSListener: LocationListener =
        LocationListener { location ->
            locationGPS = location
        }
    private var locationNetworkListener: LocationListener =
        LocationListener { location ->
            locationNetwork = location
        }

    init {
        var checkGpsSupport: Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        var checkNetworkSupported: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (checkGpsSupport) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationGPSListener,
            )
        }
        if (checkNetworkSupported) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0f,
                locationNetworkListener,
            )
        }
    }

    fun getGPSLocation(): Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

    fun getNetworkLocation(): Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

    fun getMoreAccurateLocation(): Location? {
        locationGPS = getGPSLocation()
        locationNetwork = getNetworkLocation()
        if (locationGPS == null) {
            return locationNetwork
        } else if (locationNetwork == null) {
            locationGPS
        } else {
            if (locationGPS!!.accuracy > locationNetwork!!.accuracy) {
                locationGPS
            } else {
                locationNetwork
            }
        }
        return null
    }
}
