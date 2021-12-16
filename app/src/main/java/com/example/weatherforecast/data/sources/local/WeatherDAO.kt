package com.example.weatherforecast.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.sources.local.entities.WeatherEntity

@Dao
interface WeatherDAO {

    @Query("SELECT * FROM WeatherEntity WHERE searchKey = :searchKey")
    suspend fun getAllBySearchKey(searchKey: String): List<WeatherEntity>

    @Query("DELETE FROM WeatherEntity WHERE city = :cityName")
    suspend fun deleteAllBySearchKey(cityName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertALl(weatherList: List<WeatherEntity>)

}