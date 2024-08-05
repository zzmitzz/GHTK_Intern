package com.example.android_permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.android_permission.base.BaseActivity
import com.example.android_permission.location.LocationImpl
import java.util.Locale

class LocationActivity : BaseActivity() {
    private var permissionStatus: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setView() {
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission was granted
                    permissionStatus.postValue(true)
                    updateLocation()
                } else {
                    // Permission denied
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                        )
                    ) {
                        AlertDialog
                            .Builder(this)
                            .setTitle("Permission Denied")
                            .setMessage("Permission to access location was denied permanently")
                            .setPositiveButton("Open Settings") { _, _ ->
                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            }.setNegativeButton("Cancle") { _, _ ->
                                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                            }.show()
                    }
                    return
                }
            }
        }
    }

    override fun observeLiveData() {
        permissionStatus.observe(this) {
            findViewById<TextView>(R.id.permissionStatus).apply {
                this.text = if (it) "Granted" else "Denied"
                this.setTextColor(resources.getColor(if (it) R.color.granted else R.color.denied))
            }
        }
    }

    override fun requestRuntimePermission() {
        requestPermissions(PERMISSION_LIST, 1)
    }

    override fun initListener() {
        findViewById<Button>(R.id.getButton1).setOnClickListener {
            if (getLocationPermission()) {
                updateLocation()
            } else {
                Toast.makeText(this, "You have to accept permission first ", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.buttnGrant).setOnClickListener {
            requestPermissions(PERMISSION_LIST, 1)
        }

        findViewById<Button>(R.id.getButton2).setOnClickListener {
            Toast.makeText(this, "In developing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        val locationImpl = LocationImpl(locationManager)

        val locationGps = locationImpl.getGPSLocation()
        val locationNetwork = locationImpl.getNetworkLocation()
        Log.d("Location", "GPS: ${locationGps?.latitude}, Network: ${locationNetwork?.latitude}")
        val location = locationImpl.getMoreAccurateLocation()
        Log.d("Location", "More Accurate: $location.latitude")
        if (location != null) {
            updateUI1(location)
        }
    }

    private fun getLocationPermission() =
        PERMISSION_LIST
            .all {
                ContextCompat.checkSelfPermission(
                    this,
                    it,
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            }.also {
                permissionStatus.postValue(it)
            }

    companion object {
        val PERMISSION_LIST =
            listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
            ).toTypedArray()
    }

    private fun updateUI1(location: Location) {
        findViewById<TextView>(R.id.latValue1).text = String.format(Locale.getDefault(), "%.3f", location.latitude)
        findViewById<TextView>(R.id.longValue1).text = String.format(Locale.getDefault(), "%.3f", location.longitude)
    }
}
