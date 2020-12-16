package com.rahil.poc.domain.interactor.weatherForecast

import com.rahil.poc.domain.executor.PostExecutionThread
import com.rahil.poc.domain.executor.ThreadExecutor
import com.rahil.poc.domain.interactor.FlowableUseCase
import com.rahil.poc.domain.interactor.SingleUseCase
import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.domain.repository.WeatherForecastRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class GetCityWeatherForecast @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    SingleUseCase<WeatherModel, GetCityWeatherForecast.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Single<WeatherModel> {
        return weatherForecastRepository.getWeatherDetail(params!!.cityName, params.isNetworkAvailable, params.unitType)
    }

    data class Params constructor(val cityName:String, val isNetworkAvailable:Boolean, val unitType:String) {
        companion object {
            fun fromRemote(cityName:String, isNetworkAvailable: Boolean, unitType:String): Params {
                return Params(
                    cityName, isNetworkAvailable,unitType
                )
            }
        }
    }

}
