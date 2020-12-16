package com.rahil.poc.presentation.weatherForecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahil.poc.domain.interactor.weatherForecast.AddFavouriteCityUsecase
import com.rahil.poc.domain.interactor.weatherForecast.GetCityWeatherForecast
import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.presentation.mapper.WeatherViewMapper
import com.rahil.poc.presentation.model.WeatherViewDataModel
import com.rahil.poc.presentation.state.Resource
import com.rahil.poc.presentation.state.ResourceState
import com.rahil.poc.presentation.utility.NetworkUtils
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class WeatherForeCastViewModel @Inject constructor(
    private val getCityWeatherForecast: GetCityWeatherForecast,
    private val addFavouriteCityUsecase: AddFavouriteCityUsecase,
    private val mapper: WeatherViewMapper,
    private val networkUtils: NetworkUtils

) : ViewModel() {

    private val liveData: MutableLiveData<Resource<WeatherViewDataModel>> = MutableLiveData()
    var weatherDataModel: WeatherModel ? = null

    override fun onCleared() {
        getCityWeatherForecast.dispose()
        super.onCleared()
    }

    fun getWeatherDetails(): LiveData<Resource<WeatherViewDataModel>> {
        return liveData
    }

    fun fetchWeatherData(cityName:String, units:String) {
        liveData.postValue(Resource(ResourceState.LOADING))
        getCityWeatherForecast.execute(WeatherForecastDataSubscriber(),
                GetCityWeatherForecast.Params.fromRemote(cityName.toLowerCase(), networkUtils.hasNetworkConnection(),units))

    }

    fun saveAsFavouriteCity() {
        if (weatherDataModel!= null) {
            addFavouriteCityUsecase.execute(AddFavouriteCityUsecase.Params.saveToCache(weatherDataModel!!))
                .subscribe()
        }
    }


        inner class WeatherForecastDataSubscriber : DisposableSingleObserver<WeatherModel>() {

        override fun onError(exception: Throwable) {
            var message = ""
            if (networkUtils.hasNetworkConnection()) {
                message = "Please enter correct city name or try with another city....!!!"
            } else {
                message = "Please check your internet connection."
            }
            liveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    message = message
                )
            )
        }

        override fun onSuccess(weatherModel: WeatherModel) {
            weatherDataModel =  weatherModel
            liveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    mapper.mapToView(weatherModel))
            )
        }

    }
}