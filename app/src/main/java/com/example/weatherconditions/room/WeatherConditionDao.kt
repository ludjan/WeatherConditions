package com.example.weatherconditions.room

import androidx.room.*
import com.example.weatherconditions.model.weatherCondition.WeatherCondition
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherConditionDao {

    @Query("SELECT * FROM weathercondition")
    fun getWeatherConditions(): List<WeatherCondition>

    @Query("SELECT * FROM weathercondition")
    fun getWeatherConditionsFlow(): Flow<List<WeatherCondition>>


    @Insert(onConflict = OnConflictStrategy.REPLACE) // replace works as add and update
    suspend fun insertWeatherConditions(weatherCondition: WeatherCondition)

    @Delete
    suspend fun deleteWeatherCondition(weatherCondition: WeatherCondition)

    @Query("SELECT * FROM weathercondition WHERE id = :id")
    suspend fun getWeatherConditionById(id: Int): WeatherCondition?

}