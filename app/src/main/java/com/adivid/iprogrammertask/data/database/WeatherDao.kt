package com.adivid.iprogrammertask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * from weather_table WHERE cityName LIKE '%'||:searchString ||'%' LIMIT 1")
    suspend fun searchNotes(searchString: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherEntity: WeatherEntity): Long

    @Query("SELECT cityName from weather_table")
    suspend fun getCities(): List<String>?

    @Query("DELETE FROM weather_table")
    suspend fun deleteRecords()


}