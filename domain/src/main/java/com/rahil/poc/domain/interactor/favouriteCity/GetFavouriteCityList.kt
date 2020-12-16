package com.rahil.poc.domain.interactor.favouriteCity

import com.rahil.poc.domain.executor.PostExecutionThread
import com.rahil.poc.domain.executor.ThreadExecutor
import com.rahil.poc.domain.interactor.SingleUseCase
import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.domain.repository.WeatherForecastRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFavouriteCityList @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    SingleUseCase<List<WeatherModel>, Nothing?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Single<List<WeatherModel>> {
        return weatherForecastRepository.getFavouritePlaceWeatherList()
    }
}