package me.vikas.myweather.Activity

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import me.vikas.myweather.R

class SplashActivity : AppCompatActivity() {


    private val TAG = "SplashActivity"
    private lateinit var data: Bundle

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val REQUEST_LOCATION_PERMISSION: Int = 1
    private val REQUEST_CHECK_SETTINGS: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        data = Bundle()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d(TAG, "Location update: $latitude, $longitude")
                    data.putString("latitude", latitude.toString())
                    data.putString("longitude", longitude.toString())
                }
            }
        }

        if (checkLocationPermission()) {
            requestLocationUpdates()
            getLastLocation()
        } else {
            requestLocationPermission()
        }

        Handler(mainLooper).postDelayed({
            navigateToMain()
        }, 2000)
    }

    private fun navigateToMain() {
        if (!data.isEmpty) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtras(data)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Loading data", Toast.LENGTH_SHORT).show()
            recreate()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }
    private fun getLastLocation() {
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    // Handle the location
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        Log.d(TAG, "Last known location: $latitude, $longitude")
//                        initData(latitude.toString(), longitude.toString())
                        // Do something with the latitude and longitude
                    } else {
                        Log.d(TAG, "Last known location is null")
                        // Handle the case where the last known location is not available
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle the exception
                    Log.e(TAG, "Error getting last location: ${exception.message}")
                }
        } else {
            // Request location permission
            requestLocationPermission()
        }
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000 // Update interval in milliseconds
            fastestInterval = 5000 // Fastest interval for updates
        }

        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        val settingsClient = LocationServices.getSettingsClient(this)
        val task = settingsClient.checkLocationSettings(locationSettingsRequest)

        task.addOnSuccessListener {
            // All location settings are satisfied. Start receiving updates
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // Show a dialog to the user to enable location settings
                try {
                    exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error
                }
            }
        }
    }
}