package me.vikas.myweather.Repository

import me.vikas.myweather.Model.ResponseA
import me.vikas.myweather.Util.Const
import me.vikas.myweather.Web.WebService
import retrofit2.Call

class DataRepo(val webService: WebService) {

    fun currentWeatherOfCity(city: String): Call<ResponseA?> {
        return webService.currentWeatherOf(city, unit = Const.METRIC, key = Const.KEY)
    }


    fun currentWeatherOf(latitude: String, longitude: String): Call<ResponseA?> {
        return webService.currentWeather(latitude, longitude, unit = Const.METRIC, key = Const.KEY)
    }

}