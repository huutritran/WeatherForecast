package com.example.weatherforecast.constant

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
        const val units = TemperatureUnit.Metric
    }

    object Cache {
        const val MAX_CACHE_DAYS = 2
    }

}