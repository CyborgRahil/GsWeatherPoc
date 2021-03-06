package com.rahil.poc.domain.model


data class WeatherModel(
    val city: String,
    val icon:String,
    val weatherDescription:String,
    val temp : Double,
    val feels_like : Double,
    val temp_min : Double,
    val temp_max : Double,
    val pressure : Int,
    val humidity : Int,
    val country : String,
    val sunrise : Int,
    val sunset : Int,
    val lastUpdated: Int
)