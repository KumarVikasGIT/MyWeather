package me.vikas.myweather.Util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log


@Suppress("DEPRECATION")
class CheckNetwork {
    private val TAG = "CheckNetwork"

    fun isInternetAvailable(context: Context): Boolean {
        val info =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return if (info == null) {
            Log.d(TAG, "no internet connection")
            false
        } else {
            if (info.isConnected) {
                Log.d(TAG, " internet connection available...")
                true
            } else {
                Log.d(TAG, " internet connection")
                true
            }
        }
    }
}