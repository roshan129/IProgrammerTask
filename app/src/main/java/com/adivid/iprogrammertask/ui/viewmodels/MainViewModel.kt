package com.adivid.iprogrammertask.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adivid.iprogrammertask.data.database.WeatherEntity
import com.adivid.iprogrammertask.data.repositories.MainRepository
import com.adivid.iprogrammertask.models.toWeatherEntity
import com.adivid.iprogrammertask.util.Constants.Companion.API_ID
import com.adivid.iprogrammertask.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val TAG = "MainViewModel"
    var searchNotes = MutableLiveData<Resource<WeatherEntity>>()
    var cities = MutableLiveData<Resource<List<String>?>>()

    private fun getWeatherDataFromApi(cityName: String, id: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                searchNotes.postValue(Resource.Loading())
                val weatherData = repository.getWeatherDetails(cityName, id)
                insertDataIntoDb(weatherData.toWeatherEntity())

            } catch (e: HttpException) {
                if (e.code() == 404) searchNotes.postValue(Resource.Error("City Not Found"))
                else searchNotes.postValue(Resource.Error(e.message() ?: "City Not Found"))
            } catch (e: Exception) {
                searchNotes.postValue(Resource.Error(e.message ?: "City Not Found"))
            }
        }


    fun getWeatherDataFromDb(cityName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val weatherData = repository.getWeatherDetailsFromDb(cityName)
            if (weatherData == null) {
                getWeatherDataFromApi(cityName, API_ID)
            } else {
                searchNotes.postValue(Resource.Success(weatherData))
            }
        }

    private fun insertDataIntoDb(weatherEntity: WeatherEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWeatherData(weatherEntity)
            getWeatherDataFromDb(weatherEntity.cityName)
        }

    }

    fun validateField(city: String): Boolean {
        if (city.isEmpty()) {
            return false
        }
        return true
    }

    fun setUpSuggestions() {
        viewModelScope.launch {
            cities.postValue(Resource.Success(repository.getCities()))
        }
    }

}