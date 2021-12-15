package com.example.weatherforecast.constant

import com.example.weatherforecast.constant.Config.TemperatureUnit.Imperial

class Config {
    object TemperatureUnit {
        const val Metric = "metric"
        const val Imperial = "imperial"
    }

    object DefaultQuery {
        const val numberOfDays = 7
        const val apiKey = "appid"
        const val units = Imperial
    }

}