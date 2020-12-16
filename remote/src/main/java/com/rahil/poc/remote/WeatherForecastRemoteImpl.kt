package com.rahil.poc.remote

import com.rahil.poc.data.model.WeatherEntity
import com.rahil.poc.data.repository.WeatherForecastRemote
import com.rahil.poc.remote.mapper.WeatherResponseModelMapper
import com.rahil.poc.remote.service.WeatherForecastingService
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastRemoteImpl @Inject constructor(
    private val service: WeatherForecastingService,
    private val mapper: WeatherResponseModelMapper
) : WeatherForecastRemote {
    override fun getWeatherDetails(cityName: String, unit:String): Single<WeatherEntity> {
        return service.getWeatherForecasting(cityName, unit, "186e6a6978b2f81755a9959cfd612901")
            .map { response ->mapper.mapFromModel(response) }
    }
}
