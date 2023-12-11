package me.vikas.myweather.Web

import me.vikas.myweather.Model.CityWeather
import me.vikas.myweather.Model.ResponseA
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("weather")
    fun currentWeatherOf(@Query("q") location:String,
                         @Query("units") unit: String,
                         @Query("appid") key: String): Call<ResponseA?>

    @GET("weather")
    fun currentWeather(
        @Query("lat") lantitude: String,
        @Query("lon") longitude: String,
        @Query("units") unit: String,
        @Query("appid") key: String
    ): Call<ResponseA?>

}