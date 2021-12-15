package com.example.weatherforecast.domain.models

import java.util.Date

data class WeatherInfo(
    val date: Date,
    val averageTemperature: Double,
    val pressure: Double,
    val humidity: Int,
    val description: String
)