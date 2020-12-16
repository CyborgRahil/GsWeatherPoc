package com.rahil.poc.data.source

import com.rahil.poc.data.model.WeatherEntity
import com.rahil.poc.data.repository.WeatherForecastCache
import com.rahil.poc.data.repository.WeatherForecastDataSource
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastCacheDataSource @Inject constructor(
    private val weatherForecastCache: WeatherForecastCache
) : WeatherForecastDataSource {

    override fun getFavouritePlaceWeatherList(): Single<List<WeatherEntity>> {
        return weatherForecastCache.getFavouritePlaceWeatherList()
    }

    override fun getWeatherDetail(cityName: String, unit: String) : Single<WeatherEntity> {
       return weatherForecastCache.getWeatherDetail(cityName, unit)
    }

    override fun saveWeatherDetail(weatherData: WeatherEntity): Completable {
        return weatherForecastCache.saveWeatherDetail(weatherData)
    }

    override fun clearCache(): Completable {
        return weatherForecastCache.clearCache()
    }

    override fun deleteFavouritePlace(cityName: String): Completable {
        return  weatherForecastCache.deleteFavouritePlace(cityName)
    }

    override fun isCityWeatherDataCached(cityName: String): Single<Boolean> {
        return weatherForecastCache.areCityWeatherCached(cityName)
    }

    override fun saveFavouriteCityDetail(weatherData: WeatherEntity): Completable {
        return weatherForecastCache.saveCityDetail(weatherData)
    }
}