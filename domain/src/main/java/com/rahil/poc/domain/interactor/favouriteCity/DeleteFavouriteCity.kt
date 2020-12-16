package com.rahil.poc.domain.interactor.favouriteCity

import com.rahil.poc.domain.executor.PostExecutionThread
import com.rahil.poc.domain.executor.ThreadExecutor
import com.rahil.poc.domain.interactor.CompletableUseCase
import com.rahil.poc.domain.interactor.SingleUseCase
import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.domain.repository.WeatherForecastRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DeleteFavouriteCity @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    CompletableUseCase<DeleteFavouriteCity.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Completable {
        return  weatherForecastRepository.deleteFavouritePlace(params!!.cityName)
    }


    data class Params constructor(val cityName:String) {
        companion object {
            fun fromCache(cityName:String): Params {
                return Params(
                    cityName
                )
            }
        }
    }
}