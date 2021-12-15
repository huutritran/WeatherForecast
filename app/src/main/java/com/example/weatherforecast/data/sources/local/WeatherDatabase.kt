package com.example.weatherforecast.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherforecast.data.sources.local.entities.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO

    companion object {
        const val DATABASE_NAME = "weather_search.db"
    }
}