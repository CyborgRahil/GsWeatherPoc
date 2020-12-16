package com.rahil.poc.data.repository

import com.rahil.poc.data.model.WeatherEntity
import io.reactivex.Single

interface WeatherForecastRemote {
    fun getWeatherDetails(cityName:String, unit:String): Single<WeatherEntity>
}
