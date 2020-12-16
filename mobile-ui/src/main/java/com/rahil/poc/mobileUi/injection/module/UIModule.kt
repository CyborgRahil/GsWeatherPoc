@file:Suppress("unused")

package com.rahil.poc.mobileUi.injection.module

import com.rahil.poc.domain.executor.PostExecutionThread
import com.rahil.poc.mobileUi.UIThread
import com.rahil.poc.mobileUi.favouritecity.CityListActivity
import com.rahil.poc.mobileUi.weatherForecast.HomeActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeCityListActivity(): CityListActivity

}
