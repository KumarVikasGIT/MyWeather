package me.vikas.myweather.Repository

import me.vikas.myweather.Model.ResponseA
import me.vikas.myweather.Util.Const
import me.vikas.myweather.Web.WebService
import retrofit2.Call

class DataRepo(val webService:WebService) {

//    fun currentWeatherOf(key:String,city:String ): Call<CityWeather?> {
//       return webService.currentWeather(key,city)
//    }

//    fun getCurrentWeather(latitude: String, longitude:String):Call<CurrentWeather>{
//        return webService.currentForeCast(latitude,longitude,Const._2mTemprature)
//    }

    fun currentWeatherOf(latitude: String, longitude:String): Call<ResponseA?> {
        return webService.currentWeather(latitude,longitude, unit="metric", key ="01c6156d82bbf5a9b4ee9d69319fe0c2")
    }

}