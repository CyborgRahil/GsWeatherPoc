package com.rahil.poc.remote.mapper

import WeatherData
import com.rahil.poc.data.model.WeatherEntity
import javax.inject.Inject

class WeatherResponseModelMapper @Inject constructor() : ModelMapper<WeatherData, WeatherEntity> {

    override fun mapFromModel(model: WeatherData): WeatherEntity {
        return with(model) {
            WeatherEntity(
               model.name.toLowerCase(), model.weather.get(0).icon, model.weather.get(0).description, model.main.temp,
                model.main.feels_like,model.main.temp_max, model.main.temp_min,model.main.pressure,
                model.main.humidity,model.sys.country,model.sys.sunrise+ model.timezone,model.sys.sunset + model.timezone, model.dt
            )
        }
    }
}
