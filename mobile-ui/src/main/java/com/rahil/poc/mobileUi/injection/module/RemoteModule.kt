package com.rahil.poc.mobileUi.injection.module

import com.rahil.poc.BuildConfig
import com.rahil.poc.data.repository.WeatherForecastRemote
import com.rahil.poc.remote.WeatherForecastRemoteImpl
import com.rahil.poc.remote.service.WeatherForecastingService
import com.rahil.poc.remote.service.RestApiServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideGithubService(): WeatherForecastingService {
        return RestApiServiceFactory.makeWeatherForecastingService(BuildConfig.DEBUG)
    }

    @Provides
    fun bindWeatherForecastRemote(weatherForecastRemote: WeatherForecastRemoteImpl): WeatherForecastRemote = weatherForecastRemote

}
