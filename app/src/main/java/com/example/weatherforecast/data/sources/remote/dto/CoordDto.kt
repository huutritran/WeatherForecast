package com.example.weatherforecast.data.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class CoordDto(
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("lat") var lat: Double? = null
)