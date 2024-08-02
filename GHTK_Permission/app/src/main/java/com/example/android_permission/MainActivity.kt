package com.example.android_permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData

class MainActivity : AppCompatActivity() {
    private var currentLocation: Location? = null
    private var locationByGPS: Location? = null
    private var locationByNetwork: Location? = null
    private var permissionStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val locationManager by lazy {
        getSystemService(LOCATION_SERVICE) as LocationManager
    }

    val gpsLocationListener: LocationListener =
        LocationListener { location -> locationByGPS = location }

    val networkLocationListener: LocationListener =
        LocationListener { location -> locationByNetwork = location }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
        observeLiveData()
        if (!getLocationPermission()) {
            permissionStatus.postValue(false)
            requestPermissions(PERMISSION_LIST, 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission was granted
                    permissionStatus.postValue(true)
                    initLocationRequest()
                } else {
                    // Permission denied
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        // User has permanently denied the permission
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    } else {
                        // Permission denied without permanently blocking
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
                return
            }

    }
    }
    private fun observeLiveData() {
        permissionStatus.observe(this) {
            findViewById<TextView>(R.id.permissionStatus).apply {
                this.text = if (it) "Granted" else "Denied"
                this.setTextColor(resources.getColor(if (it) R.color.granted else R.color.denied))
            }
        }
    }

    private fun initListener() {
        findViewById<Button>(R.id.getButton1).setOnClickListener {
            initLocationRequest()
        }
        findViewById<Button>(R.id.buttnGrant).setOnClickListener {
            if (!getLocationPermission()) requestPermissions(PERMISSION_LIST, 1)
        }

        findViewById<Button>(R.id.getButton2).setOnClickListener {
            Toast.makeText(this, "In developing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLocationPermission() =
        PERMISSION_LIST.all {
            ContextCompat.checkSelfPermission(
                this,
                it,
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }.also {
            permissionStatus.postValue(it)
        }

    @SuppressLint("MissingPermission")
    private fun initLocationRequest() {
        val hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (permissionStatus.value == true) {
            if (hasGPS) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    gpsLocationListener,
                )
            }
            if (hasNetwork) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    networkLocationListener,
                )
            }
        }
        Log.d("TAG", "initLocationRequest: $hasGPS $hasNetwork")
        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGPS = lastKnownLocationByGps
        }
        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }
        Log.d("TAG", "initLocationRequest: $locationByGPS $locationByNetwork")
        if (locationByGPS != null && locationByNetwork != null) {
            if (locationByGPS!!.accuracy > locationByNetwork!!.accuracy) {
                currentLocation = locationByGPS
                updateUI1(locationByGPS!!)
            } else {
                currentLocation = locationByNetwork
                updateUI1(locationByNetwork!!)
            }
        }else{
            locationByGPS?.let {
                currentLocation = it
                updateUI1(it)
                return
            }
            locationByNetwork?.let {
                currentLocation = it
                updateUI1(it)
                return
            }
        }
    }

    companion object {
        val PERMISSION_LIST =
            listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
            ).toTypedArray()
    }

    private fun updateUI1(
        location: Location
    ) {
        findViewById<TextView>(R.id.latValue1).text = String.format("%.3f", location.latitude)
        findViewById<TextView>(R.id.longValue1).text = String.format("%.3f", location.longitude)
    }

    private fun updateUI2(
        la2: Double,
        lo2: Double,
    ) {
    }
}
