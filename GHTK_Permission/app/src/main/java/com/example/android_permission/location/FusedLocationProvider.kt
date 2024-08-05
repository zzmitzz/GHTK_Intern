package com.example.android_permission.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

// require API 31
@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.S)
class FusedLocationProvider(
    val context: Context,
) : LifecycleEventObserver {
    // receiving location updates
    private var fusedLocationProviderClient: FusedLocationProviderClient

    // Specify the configuration for location request such as priority and interval
    private var locationRequest: LocationRequest

    // Callback for receiving location updates of fusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    var latitude: Double? = null
    var longitude: Double? = null
    var currentLocation: Location? = null

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest =
            LocationRequest.Builder(LocationRequest.PRIORITY_HIGH_ACCURACY, 1000).build()
    }

    private fun getLocation() {
        locationCallback =
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    currentLocation = locationResult.lastLocation
                    latitude = currentLocation?.latitude ?: 0.0
                    longitude = currentLocation?.longitude ?: 0.0

                    Log.d(TAG, "Latitude: $latitude")
                    Log.d(TAG, "Longitude: $longitude")
                }
            }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper(),
        )
    }

    private fun removeLocationUpdateCallbacks() {
        val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Location Callback removed.")
            } else {
                Log.d(TAG, "Failed to remove Location Callback.")
            }
        }
    }

    override fun onStateChanged(
        source: LifecycleOwner,
        event: Lifecycle.Event,
    ) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                getLocation()
            }

            Lifecycle.Event.ON_DESTROY -> {
                removeLocationUpdateCallbacks()
            }

            else -> {
            }
        }
    }

    companion object {
        private const val TAG = "FusedLocationProvider"
    }
}
