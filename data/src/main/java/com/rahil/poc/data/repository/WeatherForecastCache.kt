package com.rahil.poc.data.repository

import com.rahil.poc.data.model.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherForecastCache {

    fun getFavouritePlaceWeatherList(): Single<List<WeatherEntity>>

    fun getWeatherDetail(cityName: String, unit: String) : Single<WeatherEntity>

    fun saveWeatherDetail(weatherData: WeatherEntity): Completable

    fun clearCache(): Completable

    fun deleteFavouritePlace(cityName:String) : Completable

    fun areCityWeatherCached(cityName: String): Single<Boolean>

    fun saveCityDetail(weatherData: WeatherEntity): Completable
}
