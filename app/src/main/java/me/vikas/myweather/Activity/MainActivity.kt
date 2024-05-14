package me.vikas.myweather.Activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
    private var isExposed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        intent.extras?.let {
            Log.d(TAG, "Intent Extraa: ${it.getString("latitude")}")
            initData(it.getString("latitude")!!, it.getString("longitude")!!)
        }

        initGreeting()
        initSearch()
    }


    private fun initSearch() {
        dataBinding.ibSearch.setOnClickListener {
            if (!isExposed) {
                dataBinding.textField.visibility = View.VISIBLE
                dataBinding.ibSearch.setImageDrawable(getDrawable(R.drawable.ic_close))
                isExposed = true
            } else {
                dataBinding.textField.visibility = View.INVISIBLE
                dataBinding.ibSearch.setImageDrawable(getDrawable(R.drawable.ic_search))
                isExposed = false
            }
        }
        dataBinding.searchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                initDataSearch(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    private fun initData(lan: String, lon: String) {
        Log.d(TAG, "initData: $lan $lon")
        cityViewModel.getCurrentWeather(lan, lon)
            ?.observe(this, Observer {
                Log.d(TAG, "initData: ${it.toString()}")

                dataBinding.apply {

                    temprature.text = it.main?.temp?.toString()
                    clouds.text = it.weather?.get(0)?.description
                    maxTemp.text = it.main?.temp_max.toString()
                    minTemp.text = it.main?.temp_min.toString()
                    country.text = it.sys?.country
                    name.text = it.name
                    weatherClouds.text = it.weather?.get(0)?.main
                    windDirection.text = it.wind?.deg.toString()
                    windSpeed.text = it.wind?.speed.toString()

                    currentState.setImageDrawable(
                        when (it.weather?.get(0)?.id) {
                            in 200..232 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_thunder)
                            in 300..321 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_rainy_1)
                            in 500..531 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_rainy_2)
                            in 600..622 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_snowy_1)
                            in 701..781 -> ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_cloudy_day_1
                            )

                            800 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_day)
                            in 801..804 -> ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_cloudy_day_3
                            )

                            else -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_weather)
                        }
                    )
                }
            })
    }

    private fun initDataSearch(City: String) {
        cityViewModel.getCurrentWeatherOFCity(city = City)
            ?.observe(this, Observer {
                Log.d(TAG, "initData: ${it.toString()}")
                dataBinding.apply {

                    temprature.text = it.main?.temp?.toString()
                    clouds.text = it.weather?.get(0)?.description
                    maxTemp.text = it.main?.temp_max.toString()
                    minTemp.text = it.main?.temp_min.toString()
                    country.text = it.sys?.country
                    name.text = it.name
                    weatherClouds.text = it.weather?.get(0)?.main
                    windDirection.text = it.wind?.deg.toString()
                    windSpeed.text = it.wind?.speed.toString()

                    currentState.setImageDrawable(
                        when (it.weather?.get(0)?.id) {
                            in 200..232 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_thunder)
                            in 300..321 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_rainy_1)
                            in 500..531 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_rainy_2)
                            in 600..622 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_snowy_1)
                            in 701..781 -> ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_cloudy_day_1
                            )

                            800 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_day)
                            in 801..804 -> ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_cloudy_day_3
                            )

                            else -> ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_weather)
                        }
                    )
                }
            })
    }


    private fun initGreeting() {
        val c: Calendar = Calendar.getInstance();
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY);

        when (timeOfDay) {
            in 0..11 -> {
                dataBinding.current = "Good Morning"
            }
            in 12..15 -> {
                dataBinding.current = "Good Afternoon"
            }
            in 16..20 -> {
                dataBinding.current = "Good Evening"
            }
            in 21..23 -> {
                dataBinding.current = "Good Night"
            }
        }
    }
}