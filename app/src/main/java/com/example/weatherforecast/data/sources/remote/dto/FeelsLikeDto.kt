package com.example.weatherforecast.data.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class FeelsLikeDto(
    @SerializedName("day") var day: Double? = null,
    @SerializedName("night") var night: Double? = null,
    @SerializedName("eve") var eve: Double? = null,
    @SerializedName("morn") var morn: Double? = null
)