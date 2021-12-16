package com.example.weatherforecast.constant

import com.example.weatherforecast.constant.Config.TemperatureUnit.Imperial

class Config {

    object Date {
        const val defaultFormat = "EEE, dd MMM yyyy"
    }

    object TemperatureUnit {
        const val Metric = "metric"
        const val Imperial = "imperial"
    }

    object DefaultQuery {
        const val numberOfDays = 7
        const val apiKey = "appid"
        const val units = Imperial
    }

    object Cache {
        const val MAX_CACHE_DAYS = 2
    }

}