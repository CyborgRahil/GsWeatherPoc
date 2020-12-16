package com.rahil.poc.presentation.mapper

import com.rahil.poc.domain.model.WeatherModel
import com.rahil.poc.presentation.model.WeatherViewDataModel
import javax.inject.Inject

class WeatherViewMapper @Inject constructor() : Mapper<WeatherViewDataModel, WeatherModel> {
    override fun mapToView(model: WeatherModel): WeatherViewDataModel {
        return with(model) {
            WeatherViewDataModel(
                model.city, model.icon, model.weatherDescription, model.temp,
                model.feels_like,model.temp_max,model.temp_min,model.pressure,
                model.humidity,model.country,model.sunrise,model.sunset,model.lastUpdated
            )
        }
    }
}
