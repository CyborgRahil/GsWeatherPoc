package com.rahil.poc.cache.mapper

import com.rahil.poc.cache.model.CacheCityData
import com.rahil.poc.cache.model.CachedWeatherData
import com.rahil.poc.data.model.WeatherEntity
import javax.inject.Inject

class CacheCityDataMapper @Inject constructor() : CacheMapper<CacheCityData, WeatherEntity> {

    override fun mapFromCached(cachedData: CacheCityData): WeatherEntity {
        return WeatherEntity(
            cachedData.city, cachedData.icon, cachedData.weatherDescription, cachedData.temp,
            cachedData.feels_like,cachedData.temp_max,cachedData.temp_min,cachedData.pressure,
            cachedData.humidity,cachedData.country,cachedData.sunrise,cachedData.sunset,
            cachedData.lastUpdated
        )
    }

    override fun mapToCached(type: WeatherEntity): CacheCityData {
        return CacheCityData(
            type.city, type.icon, type.weatherDescription, type.temp,
            type.feels_like,type.temp_max,type.temp_min,type.pressure,
            type.humidity,type.country,type.sunrise,type.sunset,
            type.lastUpdated
        )
    }
}
