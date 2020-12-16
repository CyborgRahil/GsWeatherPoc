package com.rahil.poc.data.mapper

import com.rahil.poc.data.model.WeatherEntity
import com.rahil.poc.domain.model.WeatherModel
import javax.inject.Inject

class WeatherDetailMapper @Inject constructor() : EntityMapper<WeatherEntity, WeatherModel> {

    override fun mapFromEntity(entity: WeatherEntity): WeatherModel {
        return with(entity) {
            WeatherModel(
                entity.city, entity.icon, entity.weatherDescription, entity.temp,
                entity.feels_like,entity.temp_max,entity.temp_min,entity.pressure,
                entity.humidity,entity.country,entity.sunrise,entity.sunset, entity.lastUpdated
            )
        }
    }

    override fun mapToEntity(model: WeatherModel): WeatherEntity {
        return with(model) {
            WeatherEntity(
                model.city, model.icon, model.weatherDescription, model.temp,
                model.feels_like,model.temp_max,model.temp_min,model.pressure,
                model.humidity,model.country,model.sunrise,model.sunset, model.lastUpdated
            )
        }
    }
}