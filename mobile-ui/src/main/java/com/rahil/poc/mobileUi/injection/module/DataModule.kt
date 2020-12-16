package com.rahil.poc.mobileUi.injection.module

import com.rahil.poc.data.WeatherDataRepository
import com.rahil.poc.data.executor.JobExecutor
import com.rahil.poc.domain.executor.ThreadExecutor
import com.rahil.poc.domain.repository.WeatherForecastRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: WeatherDataRepository): WeatherForecastRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}
