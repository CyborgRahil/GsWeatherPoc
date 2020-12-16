package com.rahil.poc.domain.interactor.weatherForecast

import com.rahil.poc.domain.executor.PostExecutionThread
import com.rahil.poc.domain.executor.ThreadExecutor
import com.rahil.poc.domain.interactor.CompletableUseCase
import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.domain.repository.WeatherForecastRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddFavouriteCityUsecase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    CompletableUseCase<AddFavouriteCityUsecase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Completable {
        return  weatherForecastRepository.saveFavouriteCity(weatherData = params!!.weatherModel)
    }


    data class Params constructor(val weatherModel: WeatherModel) {
        companion object {
            fun saveToCache(weatherModel:WeatherModel): Params {
                return Params(
                    weatherModel
                )
            }
        }
    }
}