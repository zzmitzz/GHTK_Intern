package com.example.ghtkmap

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ghtkmap.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var currentLocation : Location
    private lateinit var location: Location
    private val locationManager by lazy {
        getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        requestPermissions(LIST_PERMISSION, 1)

        addAMarker()
        drawPolylineAndZoom()
    }
    private fun addAMarker(){
        location = getCurrentLocation()
        mMap.addMarker(MarkerOptions().position(LatLng(location.latitude, location.longitude)).title("Marker in your location"))
    }
    private fun drawPolylineAndZoom(){
        val GHTKBuilding = LatLng(21.014007826514074, 105.78438394043823)
        mMap.addMarker(MarkerOptions().position(GHTKBuilding).title("Marker in GHTK"))
        val polylinePoints = listOf(
            LatLng(21.014007826514074, 105.78438394043823),
            LatLng(location.latitude, location.longitude)
        )

        // Create a PolylineOptions object and add points
        val polylineOptions = PolylineOptions()
            .addAll(polylinePoints)
            .width(10f)
            .color(android.graphics.Color.GREEN)

        // Add the polyline to the map
        mMap.addPolyline(polylineOptions)

        // Build LatLngBounds to include all polyline points
        val boundsBuilder = LatLngBounds.Builder()
        for (point in polylinePoints) {
            boundsBuilder.include(point)
        }
        val bounds = boundsBuilder.build()

        // Move and zoom the camera to fit the polyline
        val padding = 100 // Padding around the bounding box in pixels
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.animateCamera(cameraUpdate)
    }
    private fun getCurrentLocation(): Location{
        val locationListener = LocationListener {
            currentLocation = it
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)

        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!

        return currentLocation
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == 0) {
                mMap.isMyLocationEnabled = true

            }
        }else{
            finish()
        }
    }
    companion object {
        private const val TAG = "MapsActivity"
        private val LIST_PERMISSION = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ).toTypedArray()
    }
}