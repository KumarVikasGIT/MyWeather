package me.vikas.myweather.Activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import me.vikas.myweather.R
import me.vikas.myweather.databinding.ActivityMainBinding
import me.vikas.myweather.viewModel.CityWeatherViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val cityViewModel: CityWeatherViewModel by viewModels()
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        intent.extras?.let {
            Log.d(TAG, "Intent Extraa: ${it.getString("latitude")}")
            initData(it.getString("latitude")!!, it.getString("longitude")!!)
        }
        initGreeting()

    }


    private fun initData(lan: String, lon: String) {
        Log.d(TAG, "initData: $lan $lon")
        cityViewModel.getCurrentWeatherOf(lan, lon)
            ?.observe(this, Observer {
                Log.d(TAG, "initData: ${it.toString()}")
                dataBinding.temprature.text = it.main?.temp?.toString()
                dataBinding.clouds.text = it.weather?.get(0)?.description
                dataBinding.maxTemp.text = it.main?.temp_max.toString()
                dataBinding.minTemp.text = it.main?.temp_min.toString()
                dataBinding.country.text = it.sys?.country
                dataBinding.name.text = it.name
                dataBinding.weatherClouds.text = it.weather?.get(0)?.main
                dataBinding.windDirection.text = it.wind?.deg.toString()
                dataBinding.windSpeed.text = it.wind?.speed.toString()

                dataBinding.currentState.setImageDrawable(
                    when (it.weather?.get(0)?.id) {
                        in 200..232 -> ContextCompat.getDrawable(this, R.drawable.ic_thunder)
                        in 300..321 -> ContextCompat.getDrawable(this, R.drawable.ic_rainy_1)
                        in 500..531 -> ContextCompat.getDrawable(this, R.drawable.ic_rainy_2)
                        in 600..622 -> ContextCompat.getDrawable(this, R.drawable.ic_snowy_1)
                        in 701..781 -> ContextCompat.getDrawable(this, R.drawable.ic_cloudy_day_1)
                        800 -> ContextCompat.getDrawable(this, R.drawable.ic_day)
                        in 801..804 -> ContextCompat.getDrawable(this, R.drawable.ic_cloudy_day_3)
                        else -> ContextCompat.getDrawable(this, R.drawable.ic_weather)
                    }
                )

            })
    }


    fun initGreeting() {
        var c: Calendar = Calendar.getInstance();
        var timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay in 0..11) {
            dataBinding.current = "Good Morning"
        } else if (timeOfDay in 12..15) {
            dataBinding.current = "Good Afternoon"
        } else if (timeOfDay in 16..20) {
            dataBinding.current = "Good Evening"
        } else if (timeOfDay in 21..23) {
            dataBinding.current = "Good Night"
        }
    }
}