package com.rahil.poc.data

import com.rahil.poc.data.mapper.WeatherDetailMapper
import com.rahil.poc.data.repository.WeatherForecastCache
import com.rahil.poc.data.source.WeatherForecastDataSourceFactory
import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.domain.repository.WeatherForecastRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val mapper: WeatherDetailMapper,
    private val cache: WeatherForecastCache,
    private val factory: WeatherForecastDataSourceFactory
) : WeatherForecastRepository {
    override fun getWeatherDetail(cityName: String, networkConnection: Boolean,unit:String): Single<WeatherModel> {
        return factory.getCacheDataSource().isCityWeatherDataCached(cityName)
            .flatMap {
                factory.getDataSource(it || !networkConnection).getWeatherDetail(cityName, unit)
                    .map { mapper.mapFromEntity(it) }
            }. flatMap {
                saveCityWeather(it).toSingle { it }
            }
    }

    override fun getFavouritePlaceWeatherList(): Single<List<WeatherModel>> {
       return  factory.getCacheDataSource().getFavouritePlaceWeatherList()
           .map { it.map { mapper.mapFromEntity(it) }}
    }

    override fun deleteFavouritePlace(cityName: String): Completable {
        return  cache.deleteFavouritePlace(cityName)
    }

    override fun saveCityWeather(weatherData: WeatherModel): Completable {
        return cache.saveWeatherDetail(mapper.mapToEntity(weatherData))
    }

    override fun saveFavouriteCity(weatherData: WeatherModel): Completable {
        return cache.saveCityDetail(mapper.mapToEntity(weatherData))
    }
}



