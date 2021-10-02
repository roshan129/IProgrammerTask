package com.adivid.iprogrammertask.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adivid.iprogrammertask.util.Constants.Companion.WEATHER_TABLE
import java.text.DateFormat

@Entity(tableName = WEATHER_TABLE)
data class WeatherEntity(
    val cityName: String,
    val maxTemp: Double,
    val minTemp: Double,
    val dateTime: Long = System.currentTimeMillis(),
) {
    @PrimaryKey
    var id: Int? = null
    val dateTimeFormatted: String
        get() = DateFormat.getDateTimeInstance().format(dateTime)

}