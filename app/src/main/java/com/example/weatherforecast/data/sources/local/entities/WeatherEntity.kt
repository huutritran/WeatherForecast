package com.example.weatherforecast.data.sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherforecast.domain.models.WeatherInfo
import java.util.Date

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val date: Date,
    val averageTemperature: Double,
    val pressure: Double,
    val humidity: Int,
    val description: String,
    val createdDate: Date
) {
    fun toWeatherInfo(): WeatherInfo {
        return WeatherInfo(
            date = date,
            averageTemperature = averageTemperature,
            pressure = pressure,
            humidity = humidity,
            description = description
        )
    }
}