package me.vikas.myweather.Util

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Const {


//    const val KEY:String="9e35080a4ab141778ab105757230112"
//    const val BASE_URL:String="https://api.open-meteo.com/v1/"
//    const val _2mTemprature:String="temperature_2m,apparent_temperature,is_day,rain,showers,snowfall,cloud_cover,wind_speed_10m,wind_direction_10m"


    const val KEY: String = "01c6156d82bbf5a9b4ee9d69319fe0c2"
    const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
    const val METRIC: String = "metric"
}

fun showAlertDialogue(context: Context, title: String, content: String, onRetry: () -> Unit, onCancel: () -> Unit) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(content)
        .setPositiveButton("Retry", DialogInterface.OnClickListener({ dialog, which -> onRetry() }))
        .setNegativeButton("cancel"
        ) { dialog, which -> onCancel() }
        .show()
}