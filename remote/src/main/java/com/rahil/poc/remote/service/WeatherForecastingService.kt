package com.rahil.poc.remote.service

import WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastingService {

    @GET("weather")
    fun getWeatherForecasting(@Query("q") cityName:String,@Query("units") units:String, @Query("appid") appId:String)
            : Single<WeatherData>
}
