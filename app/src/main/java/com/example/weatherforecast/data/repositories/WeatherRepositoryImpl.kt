package com.example.weatherforecast.data.repositories

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.constant.Config
import com.example.weatherforecast.constant.Config.DefaultQuery
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.data.sources.local.WeatherDAO
import com.example.weatherforecast.data.sources.local.entities.WeatherEntity
import com.example.weatherforecast.data.sources.remote.WeatherApi
import com.example.weatherforecast.data.sources.remote.WeatherApi.QueryKey
import com.example.weatherforecast.data.sources.remote.dto.DailyWeatherResponse
import com.example.weatherforecast.di.IODispatcher
import com.example.weatherforecast.domain.models.WeatherInfo
import com.example.weatherforecast.domain.repositories.WeatherRepository
import com.example.weatherforecast.extension.diffDays
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Date
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherDAO: WeatherDAO,
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getDailyForecast(city: String): Either<Failure, List<WeatherInfo>> =
        withContext(ioDispatcher) {
            val diskCache = weatherDAO.getAllBySearchKey(city)

            if (isCacheValid(diskCache))
                return@withContext Right(diskCache.map { it.toWeatherInfo() })

            if (diskCache.isNotEmpty()) {
                weatherDAO.deleteAllBySearchKey(city)
            }

            val queryOptions = getQueryOptions(city)
            kotlin.runCatching { weatherApi.getDailyWeather(queryOptions) }
                .fold(
                    onSuccess = { data -> handleSuccess(data, city) },
                    onFailure = this@WeatherRepositoryImpl::handleFailure
                )
        }

    private fun getQueryOptions(city: String): Map<String, String> {
        return mapOf(
            QueryKey.searchCriteria to city,
            QueryKey.numberOfDays to DefaultQuery.numberOfDays.toString(),
            QueryKey.apiKey to BuildConfig.API_KEY,
            QueryKey.units to DefaultQuery.units
        )
    }

    private fun isCacheValid(cache: List<WeatherEntity>): Boolean {
        if (cache.isEmpty())
            return false

        val cacheDate = cache.first().createdDate
        val today = Date()
        if (cacheDate.diffDays(today) > Config.Cache.MAX_CACHE_DAYS)
            return false

        return true
    }

    private fun handleSuccess(response: DailyWeatherResponse, searchKey: String): Right<List<WeatherInfo>> {
        if (response.weatherList.isNotEmpty()) {
            val entities = response.toWeatherEntityList(searchKey)
            weatherDAO.insertALl(entities)
        }
        return Right(response.toWeatherInfoList())
    }

    private fun handleFailure(throwable: Throwable): Left<Failure> = when (throwable) {
        is HttpException -> Left(Failure.ServerError)
        else -> Left(Failure.NetworkConnection)
    }
}