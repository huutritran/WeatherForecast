package com.example.weatherforecast.domain.usecases

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.flatMap
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.core.UseCase
import com.example.weatherforecast.domain.models.WeatherInfo
import com.example.weatherforecast.domain.repositories.WeatherRepository

class SearchWeatherFailure {
    data class SearchMinLength(val validValue: Int) : Failure.FeatureFailure()
    object NotFound : Failure.FeatureFailure()
}
typealias SearchMinLength = SearchWeatherFailure.SearchMinLength
typealias NotFound = SearchWeatherFailure.NotFound

class SearchWeather(
    private val weatherRepository: WeatherRepository
) : UseCase<List<WeatherInfo>, SearchWeather.Params>() {

    companion object {
        const val MIN_CITY_LENGTH = 3
    }

    override suspend fun invoke(params: Params): Either<Failure, List<WeatherInfo>> {
        if (params.city.length < MIN_CITY_LENGTH) {
            return Left(SearchMinLength(MIN_CITY_LENGTH))
        }

        return weatherRepository.getDailyForecast(params.city).flatMap {
            when {
                it.isEmpty() -> Left(NotFound)
                else -> Right(it)
            }
        }
    }

    data class Params(
        val city: String
    )

}