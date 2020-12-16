package com.rahil.poc.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahil.poc.cache.dao.CachedFavouriteCityDao
import com.rahil.poc.cache.dao.CachedWeatherDao
import com.rahil.poc.cache.model.CacheCityData
import com.rahil.poc.cache.model.CachedWeatherData
import com.rahil.poc.cache.model.SingletonHolder

@Database(
    entities = arrayOf(CachedWeatherData::class, CacheCityData::class),
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cachedWeatherDao(): CachedWeatherDao
    abstract fun cachedFavouriteCityDao(): CachedFavouriteCityDao

    companion object : SingletonHolder<Context, WeatherDatabase>({
        Room.databaseBuilder(
            it.applicationContext,
            WeatherDatabase::class.java,
            "WeatherForecast.db"
        ).build()
    })
}