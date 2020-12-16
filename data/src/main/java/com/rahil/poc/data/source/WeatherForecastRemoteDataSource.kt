package com.rahil.poc.data.source

import com.rahil.poc.data.model.WeatherEntity
import com.rahil.poc.data.repository.WeatherForecastDataSource
import com.rahil.poc.data.repository.WeatherForecastRemote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastRemoteDataSource @Inject constructor(
    private val weatherForecastRemote: WeatherForecastRemote
) : WeatherForecastDataSource {
    override fun getFavouritePlaceWeatherList(): Single<List<WeatherEntity>> {
        throw UnsupportedOperationException("getFavouritePlaceWeatherList isn't supported here...")
    }

    override fun getWeatherDetail(cityName: String, unit: String): Single<WeatherEntity> {
        return weatherForecastRemote.getWeatherDetails(cityName, unit)
    }

    override fun saveWeatherDetail(weatherData: WeatherEntity): Completable {
        throw UnsupportedOperationException("Saving weatherData isn't supported here...")
    }

    override fun clearCache(): Completable {
        throw UnsupportedOperationException("Clearing weatherData isn't supported here...")
    }

    override fun deleteFavouritePlace(cityName: String): Completable {
        throw UnsupportedOperationException("deleteFavouritePlace weatherData isn't supported here...")
    }

    override fun isCityWeatherDataCached(cityName: String): Single<Boolean> {
        throw UnsupportedOperationException("this weatherData api isn't supported here...")
    }

    override fun saveFavouriteCityDetail(weatherData: WeatherEntity): Completable {
        throw UnsupportedOperationException("this weatherData api isn't supported here...")
    }
}