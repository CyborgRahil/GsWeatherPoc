package com.rahil.poc.mobileUi.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahil.poc.mobileUi.injection.ViewModelFactory
import com.rahil.poc.presentation.favouriteCity.FavouriteCityViewModel
import com.rahil.poc.presentation.weatherForecast.WeatherForeCastViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherForeCastViewModel::class)
    abstract fun bindWeatherForeCastViewModel(viewModel: WeatherForeCastViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteCityViewModel::class)
    abstract fun bindFavouriteCityViewModel(viewModel: FavouriteCityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)