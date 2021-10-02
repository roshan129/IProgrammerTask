package com.adivid.iprogrammertask.data.network

import com.adivid.iprogrammertask.models.WeatherResult
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeatherDetails(
        @Query("q") city_name: String,
        @Query("appid") app_id: String
    ): WeatherResult


}