package com.example.weatherforecast.data.sources.remote

import com.example.weatherforecast.data.sources.remote.dto.DailyWeatherResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    private companion object {
        const val FORECAST = "/data/2.5/forecast/daily"
    }

    object QueryKey {
        const val searchCriteria = "q"
        const val numberOfDays = "cnt"
        const val apiKey = "appid"
        const val units = "units"
    }

    @GET(FORECAST)
    suspend fun getDailyWeather(
        @QueryMap options: Map<String, String>
    ): DailyWeatherResponse

}