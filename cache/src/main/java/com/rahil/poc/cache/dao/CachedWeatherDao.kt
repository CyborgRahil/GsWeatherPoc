package com.rahil.poc.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahil.poc.cache.db.constants.DataBaseConstants
import com.rahil.poc.cache.model.CachedWeatherData
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CachedWeatherDao {

    @Query("SELECT EXISTS(SELECT 1 FROM ${DataBaseConstants.Weather_Table_NAME} WHERE city = :cityName)")
    fun cachedCityWeatherExist(cityName:String): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertWeatherDetail(cachedWeatherData: CachedWeatherData)

    @Query("DELETE FROM  ${DataBaseConstants.Weather_Table_NAME}")
    fun deleteAllWeatherDetails()

    @Query("Select * FROM CityWeather WHERE city = :cityName")
    fun getWeatherDetail(cityName:String): Single<CachedWeatherData>
}
