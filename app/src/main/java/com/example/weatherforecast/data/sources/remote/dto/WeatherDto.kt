package com.example.weatherforecast.data.sources.remote.dto

import com.example.weatherforecast.domain.models.WeatherInfo
import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherDto(
    @SerializedName("dt") var dt: Long,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null,
    @SerializedName("temp") var tempDto: TempDto? = TempDto(),
    @SerializedName("feels_like") var feelsLikeDto: FeelsLikeDto? = FeelsLikeDto(),
    @SerializedName("pressure") var pressure: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("weather") var weather: List<WeatherAdditionalInfo> = arrayListOf(),
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null,
    @SerializedName("gust") var gust: Double? = null,
    @SerializedName("clouds") var clouds: Int? = null,
    @SerializedName("pop") var pop: Double? = null
) {
    fun toWeatherInfo(): WeatherInfo {
        return WeatherInfo(
            date = Date(dt),
            averageTemperature = tempDto?.day ?: 0.0,
            pressure = pressure ?: 0.0,
            humidity = humidity ?: 0,
            description = weather.firstOrNull()?.description.orEmpty()
        )
    }
}