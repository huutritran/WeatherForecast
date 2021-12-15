package com.example.weatherforecast.domain.entities

import java.util.Date

data class WeatherInfo(
    val date: Date,
    val averageTemperature: String,
    val pressure: Double,
    val humidity: Double,
    val description: String
)