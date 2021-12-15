package com.example.weatherforecast.data.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("coord") var coord: CoordDto? = CoordDto(),
    @SerializedName("country") var country: String? = null,
    @SerializedName("population") var population: Int? = null,
    @SerializedName("timezone") var timezone: Int? = null
)