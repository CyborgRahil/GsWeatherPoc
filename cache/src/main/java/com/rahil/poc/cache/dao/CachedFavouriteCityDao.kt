package com.rahil.poc.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahil.poc.cache.db.constants.DataBaseConstants
import com.rahil.poc.cache.model.CacheCityData
import com.rahil.poc.cache.model.CachedWeatherData
import io.reactivex.Completable
import io.reactivex.Single
import org.jetbrains.annotations.NotNull

@Dao
interface CachedFavouriteCityDao {

    @Query("Select * FROM ${DataBaseConstants.City_Table_NAME}")
    @JvmSuppressWildcards
    fun getFavouritePlaceWeatherList() : Single<List<CacheCityData>>

    @Query("SELECT EXISTS(SELECT 1 FROM ${DataBaseConstants.City_Table_NAME} WHERE city = :cityName)")
    fun cachedCityWeatherExist(cityName:String): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertCityDetail(cachedCityData: CacheCityData)

    @Query("DELETE FROM ${DataBaseConstants.City_Table_NAME} WHERE city = :cityName")
    fun deleteCityDetails(cityName:String)

    @Query("Select * FROM ${DataBaseConstants.City_Table_NAME} WHERE city = :cityName")
    fun getCityDetail(cityName:String): Single<CachedWeatherData>
}