package com.adivid.iprogrammertask.data.repositories

import android.util.Log
import com.adivid.iprogrammertask.data.database.WeatherDao
import com.adivid.iprogrammertask.data.database.WeatherEntity
import com.adivid.iprogrammertask.data.network.WeatherApi
import com.adivid.iprogrammertask.models.WeatherResult
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) {


    suspend fun getWeatherDetails(city: String, id: String): WeatherResult {
        return api.getWeatherDetails(city, id)
    }

    suspend fun getWeatherDetailsFromDb(city: String): WeatherEntity? {
        return dao.searchNotes(city)
    }

    suspend fun insertWeatherData(entity: WeatherEntity): Long {
        return dao.insertWeatherData(entity)
    }

    suspend fun getCities():  List<String>? {
        return dao.getCities()
    }
}