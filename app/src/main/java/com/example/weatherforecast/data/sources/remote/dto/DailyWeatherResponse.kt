package com.example.weatherforecast.data.sources.remote.dto

import com.example.weatherforecast.data.sources.local.entities.WeatherEntity
import com.example.weatherforecast.domain.models.WeatherInfo
import com.google.gson.annotations.SerializedName
import java.util.Date

data class DailyWeatherResponse(
    @SerializedName("city") var cityDto: CityDto? = CityDto(),
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Double? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var weatherList: List<WeatherDto> = arrayListOf()
) {

    fun toWeatherInfoList(): List<WeatherInfo> = weatherList.map { it.toWeatherInfo() }

    fun toWeatherEntityList(searchKey: String): List<WeatherEntity> {
        return weatherList.map {
            WeatherEntity(
                searchKey = searchKey,
                city = cityDto?.name.orEmpty(),
                date = Date(it.dt),
                averageTemperature = it.tempDto?.day ?: 0.0,
                pressure = it.pressure ?: 0.0,
                humidity = it.humidity ?: 0,
                description = it.weather.firstOrNull()?.description.orEmpty(),
                createdDate = Date()
            )
        }
    }
}