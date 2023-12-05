package me.vikas.myweather.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.vikas.myweather.Model.ResponseA
import me.vikas.myweather.Repository.DataRepo
import me.vikas.myweather.Web.WebClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityWeatherViewModel(application: Application) : AndroidViewModel(application) {

    var dataRepo: DataRepo

    init {
        val webService = WebClient.apiClient
        dataRepo = DataRepo(webService)
    }

    fun getCurrentWeatherOf(latitude: String, longitude: String): LiveData<ResponseA> {
        val cityWeather: MutableLiveData<ResponseA> = MutableLiveData()

        dataRepo.currentWeatherOf(latitude, longitude).enqueue(object : Callback<ResponseA?> {
            override fun onResponse(
                call: Call<ResponseA?>,
                response: Response<ResponseA?>
            ) {
                Log.d("TAG", "onResponse: ${response.code()}")
                if (response.code() == 200) {
                    response.body()?.let {
                        cityWeather.value = it
                    }
                }
            }

            override fun onFailure(call: Call<ResponseA?>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return cityWeather
    }

//    fun getCurrentWeather(lantitude: String, longitude: String): LiveData<CurrentWeather>? {
//        var currentWeather: MutableLiveData<CurrentWeather>? = null
//
//        dataRepo.getCurrentWeather(lantitude, longitude)
//            .enqueue(object : Callback<CurrentWeather?> {
//                override fun onResponse(
//                    call: Call<CurrentWeather?>,
//                    response: Response<CurrentWeather?>
//                ) {
//                    if (response.code() == 200) {
//                        response.body()?.let {
//                            currentWeather?.value = it
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<CurrentWeather?>, t: Throwable) {
//
//                }
//            })
//        return currentWeather
//    }
}