package com.example.weatherforecast.data.repositories

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.example.weatherforecast.constant.Config.DefaultQuery
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.data.sources.local.WeatherDAO
import com.example.weatherforecast.data.sources.remote.WeatherApi
import com.example.weatherforecast.data.sources.remote.WeatherApi.QueryKey
import com.example.weatherforecast.data.sources.remote.dto.DailyWeatherResponse
import com.example.weatherforecast.domain.models.WeatherInfo
import com.example.weatherforecast.domain.repositories.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class WeatherRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val weatherDAO: WeatherDAO,
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getDailyForecast(city: String): Either<Failure, List<WeatherInfo>> =
        withContext(ioDispatcher) {
            val diskCache = weatherDAO.getAllByCity(city)

            if (diskCache.isNotEmpty())
                return@withContext Right(diskCache.map { it.toWeatherInfo() })

            val queryOptions = mapOf(
                QueryKey.searchCriteria to city,
                QueryKey.numberOfDays to DefaultQuery.numberOfDays.toString(),
                QueryKey.apiKey to "",
                QueryKey.units to DefaultQuery.units
            )

            kotlin.runCatching { weatherApi.getDailyWeather(queryOptions) }
                .fold(
                    onSuccess = this@WeatherRepositoryImpl::handleSuccess,
                    onFailure = this@WeatherRepositoryImpl::handleFailure
                )
        }

    private fun handleSuccess(response: DailyWeatherResponse): Right<List<WeatherInfo>> {
        if (response.weatherList.isNotEmpty()) {
            weatherDAO.insertALl(response.toWeatherEntityList())
        }
        return Right(response.toWeatherInfoList())
    }

    private fun handleFailure(throwable: Throwable): Left<Failure> = when (throwable) {
        is HttpException -> Left(Failure.ServerError)
        else -> Left(Failure.NetworkConnection)
    }
}