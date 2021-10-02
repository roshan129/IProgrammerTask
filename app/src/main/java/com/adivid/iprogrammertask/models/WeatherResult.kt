package com.adivid.iprogrammertask.models

import com.adivid.iprogrammertask.data.database.WeatherEntity

data class WeatherResult(

    val id: Int,
    val main: Main,
    val name: String,

)

fun WeatherResult.toWeatherEntity(): WeatherEntity {

    return WeatherEntity(name, main.temp_max, main.temp_min, System.currentTimeMillis())

}