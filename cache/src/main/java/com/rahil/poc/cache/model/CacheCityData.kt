package com.rahil.poc.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rahil.poc.cache.db.constants.DataBaseConstants

@Entity(tableName = DataBaseConstants.City_Table_NAME)
data class CacheCityData(
    @PrimaryKey
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
    val lastUpdated:Int
)