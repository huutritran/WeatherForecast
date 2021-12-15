package com.example.weatherforecast.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherforecast.data.sources.local.entities.WeatherEntity

@Dao
interface WeatherDAO {

    @Query("SELECT * FROM WeatherEntity WHERE city = :cityName")
    suspend fun getAllByCity(cityName: String): List<WeatherEntity>

    @Query("DELETE FROM WeatherEntity WHERE city = :cityName")
    suspend fun deleteAllByCity(cityName: String)

    @Insert
    fun insertALl(weatherList: List<WeatherEntity>)
}