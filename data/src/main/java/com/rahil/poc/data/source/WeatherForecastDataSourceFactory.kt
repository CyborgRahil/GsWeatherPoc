package com.rahil.poc.data.source

import com.rahil.poc.data.repository.WeatherForecastDataSource
import javax.inject.Inject

open class WeatherForecastDataSourceFactory @Inject constructor(
    private val weatherForecastCacheDataSource: WeatherForecastCacheDataSource,
    private val weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource
) {
    open fun getDataSource(
        isCache: Boolean
    ): WeatherForecastDataSource {
        return if (isCache) {
            weatherForecastCacheDataSource
        } else {
            weatherForecastRemoteDataSource
        }
    }

    open fun getCacheDataSource(): WeatherForecastDataSource {
        return weatherForecastCacheDataSource
    }
}
