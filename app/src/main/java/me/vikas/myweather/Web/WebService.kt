package me.vikas.myweather.Web

import me.vikas.myweather.Model.CityWeather
import me.vikas.myweather.Model.ResponseA
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
//    @GET("current.json")
//    fun currentWeatherOf(@Query("key") key:String,
//                         @Query("q") location:String): Call<CityWeather?>

//    @GET("forecast")
//    fun currentForeCast(@Query("latitude") lan:String,
//                        @Query("longitude") lon:String,
//                        @Query("current") current: String):Call<CurrentWeather>

    @GET("weather")
    fun currentWeather(
        @Query("lat") lantitude: String,
        @Query("lon") longitude: String,
        @Query("units") unit: String,
        @Query("appid") key: String
    ): Call<ResponseA?>

}