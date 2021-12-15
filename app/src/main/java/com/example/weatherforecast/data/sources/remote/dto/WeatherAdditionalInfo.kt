package com.example.weatherforecast.data.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherAdditionalInfo(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null
)