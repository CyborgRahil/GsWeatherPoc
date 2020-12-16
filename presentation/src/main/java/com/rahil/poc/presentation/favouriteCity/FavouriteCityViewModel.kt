package com.rahil.poc.presentation.favouriteCity

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahil.poc.domain.interactor.favouriteCity.DeleteFavouriteCity
import com.rahil.poc.domain.interactor.favouriteCity.GetFavouriteCityList

import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.presentation.mapper.WeatherViewMapper
import com.rahil.poc.presentation.model.WeatherViewDataModel
import com.rahil.poc.presentation.state.Resource
import com.rahil.poc.presentation.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class FavouriteCityViewModel @Inject constructor(
    private val getFavouriteCityList: GetFavouriteCityList,
    private val deleteFavouriteCity: DeleteFavouriteCity,
    private val mapper: WeatherViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<WeatherViewDataModel>>> = MutableLiveData()

    override fun onCleared() {
        getFavouriteCityList.dispose()
        super.onCleared()
    }

    fun getFavouriteCity(): LiveData<Resource<List<WeatherViewDataModel>>> {
        return liveData
    }

    fun fetchAllFavouriteCity() {
        liveData.postValue(Resource(ResourceState.LOADING))
        getFavouriteCityList.execute(CityDataSubscriber())
    }

    @SuppressLint("CheckResult")
    fun deleteFavouriteCity(cityName:String) {
        deleteFavouriteCity.execute(DeleteFavouriteCity.Params.fromCache(cityName.toLowerCase()))
            .subscribe {
            }
    }

    inner class CityDataSubscriber : DisposableSingleObserver<List<WeatherModel>>() {

        override fun onError(exception: Throwable) {
            liveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    message = exception.localizedMessage
                )
            )
        }

        override fun onSuccess(data: List<WeatherModel>) {
            liveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    data = data.map { mapper.mapToView(it) })
            )
        }

    }
}