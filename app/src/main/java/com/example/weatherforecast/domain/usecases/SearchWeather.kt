package com.example.weatherforecast.domain.usecases

import arrow.core.Either
import arrow.core.Either.Left
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.core.UseCase
import com.example.weatherforecast.domain.entities.WeatherInfo
import com.example.weatherforecast.domain.repositories.WeatherRepository

class SearchWeatherFailure {
    data class SearchMinLength(val validValue: Int) : Failure.FeatureFailure()
}

class SearchWeather(
    private val weatherRepository: WeatherRepository
): UseCase<List<WeatherInfo>, SearchWeather.Params>() {

    companion object {
        const val MIN_CITY_LENGTH = 3
    }

    override suspend fun invoke(params: Params): Either<Failure, List<WeatherInfo>> {
        return when {
            params.city.length < MIN_CITY_LENGTH -> Left(
                SearchWeatherFailure.SearchMinLength(
                    MIN_CITY_LENGTH
                )
            )
            else -> weatherRepository.getDailyForecast(params.city)
        }
    }

    data class Params(
        val city: String
    )

}