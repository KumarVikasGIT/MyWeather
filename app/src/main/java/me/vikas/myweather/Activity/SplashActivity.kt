package me.vikas.myweather.Activity

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
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
import me.vikas.myweather.Util.CheckNetwork
import me.vikas.myweather.Util.showAlertDialogue

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        data = Bundle()

        if(!CheckNetwork().isInternetAvailable(this)){
            showAlertDialogue(this, "Network Error", "Please make sure you are connected to internet.",
                onRetry = { recreate() }){
                finish()
            }
        }

        if (checkLocationPermission()) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
            requestLocationUpdates()
            getLastLocation()
        } else {
            requestLocationPermission()
            return
        }

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
            getLastLocation()
            requestLocationUpdates()
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
                    } else {
                        Log.d(TAG, "Last known location is null")
                    }
                }
                .addOnFailureListener { exception ->
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
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            navigateToMain()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
//                    exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
//                    showPermissionDeniedDialog()
                    return@addOnFailureListener
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "requestLocationUpdates: ${sendEx.message}")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates()
                getLastLocation()
            } else {
                showPermissionDeniedDialog()
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        showAlertDialogue(this, "Permission Denied", 
            "Location permission is required for this app. Please enable it in the app settings.",
            onRetry = { openAppSettings() }){
            finish()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        if (!checkLocationPermission()){
            showPermissionDeniedDialog()
            return
        }
        else navigateToMain()
    }
}