package me.vikas.myweather.Web

import me.vikas.myweather.Util.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebClient {

   private val instance by lazy {
        Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiClient by lazy {
        instance.create(WebService::class.java)
    }

}