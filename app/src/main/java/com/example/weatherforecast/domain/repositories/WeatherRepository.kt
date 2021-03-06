package com.example.weatherforecast.domain.repositories

import arrow.core.Either
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.domain.models.WeatherInfo

interface WeatherRepository {
    suspend fun getDailyForecast(city: String): Either<Failure, List<WeatherInfo>>
}
