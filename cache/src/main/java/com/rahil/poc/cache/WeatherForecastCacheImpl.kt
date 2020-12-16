package com.rahil.poc.cache

import com.rahil.poc.cache.db.WeatherDatabase
import com.rahil.poc.cache.mapper.CacheCityDataMapper
import com.rahil.poc.cache.mapper.CachedWeatherDataMapper
import com.rahil.poc.data.model.WeatherEntity
import com.rahil.poc.data.repository.WeatherForecastCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class WeatherForecastCacheImpl @Inject constructor(
    private val weatherDatabase: WeatherDatabase,
    private val weatherMapper: CachedWeatherDataMapper,
    private val cityMapper: CacheCityDataMapper
) : WeatherForecastCache {

    override fun getFavouritePlaceWeatherList(): Single<List<WeatherEntity>> {
        return weatherDatabase.cachedFavouriteCityDao().getFavouritePlaceWeatherList()
            .map { list -> list.map { cityMapper.mapFromCached(it) } }
    }

    override fun getWeatherDetail(cityName: String, unit: String): Single<WeatherEntity> {
        return weatherDatabase.cachedWeatherDao().getWeatherDetail(cityName)
            .map { (weatherMapper.mapFromCached(it))}
    }

    override fun saveWeatherDetail(weatherData: WeatherEntity): Completable {
        return  Completable.defer {
            weatherDatabase.cachedWeatherDao().insertWeatherDetail(weatherMapper.mapToCached(weatherData))
            Completable.complete()
        }
    }

    override fun clearCache(): Completable {
        return Completable.fromCallable {
            weatherDatabase.cachedWeatherDao().deleteAllWeatherDetails()
        }
    }

    override fun deleteFavouritePlace(cityName: String): Completable {
        return Completable.fromCallable {
            weatherDatabase.cachedFavouriteCityDao().deleteCityDetails(cityName)
        }
    }

    override fun areCityWeatherCached(cityName: String): Single<Boolean> {
        return weatherDatabase.cachedWeatherDao()
            .cachedCityWeatherExist(cityName)
            .map { it > 0 }
    }

    override fun saveCityDetail(weatherData: WeatherEntity): Completable {
        return Completable.fromCallable {
            weatherDatabase.cachedFavouriteCityDao().insertCityDetail(cityMapper.mapToCached(weatherData))
        }
    }
}
