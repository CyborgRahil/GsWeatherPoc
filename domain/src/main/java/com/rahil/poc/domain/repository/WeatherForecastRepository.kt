package com.rahil.poc.domain.repository

import com.rahil.poc.domain.model.WeatherModel
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherForecastRepository {
    fun getWeatherDetail(cityName: String, networkConnection: Boolean, unit:String): Single<WeatherModel>
    fun getFavouritePlaceWeatherList(): Single<List<WeatherModel>>
    fun deleteFavouritePlace(cityName:String) : Completable
    fun saveCityWeather(weatherData: WeatherModel): Completable
    fun saveFavouriteCity(weatherData:WeatherModel): Completable
}