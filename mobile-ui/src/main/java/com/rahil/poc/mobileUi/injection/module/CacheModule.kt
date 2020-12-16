package com.rahil.poc.mobileUi.injection.module

import android.content.Context
import com.rahil.poc.cache.WeatherForecastCacheImpl
import com.rahil.poc.cache.db.WeatherDatabase
import com.rahil.poc.data.repository.WeatherForecastCache
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    @Provides
    fun providesWeatherDatabase(context: Context): WeatherDatabase {
        return WeatherDatabase.getInstance(context)
    }

    @Provides
    fun providesWeatherForecastCache(weatherForecastCache: WeatherForecastCacheImpl): WeatherForecastCache = weatherForecastCache
}
