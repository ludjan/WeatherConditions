package com.example.weatherconditions

import com.example.weatherconditions.model.GeoLocation
import com.example.weatherconditions.model.watherCondition.WeatherCondition
import kotlinx.coroutines.flow.Flow

// Repository should have the access to all Datasources, and decide what to forward to viewModels

interface WeatherConditionRepository {

    suspend fun getLocationForecast(): LocationForecast

    suspend fun getGeoLocation(): GeoLocation

    suspend fun insertWeatherCondition(weatherCondition: WeatherCondition)

    suspend fun deleteWeatherCondition(weatherCondition: WeatherCondition)

    suspend fun getWeatherConditionById(id: Int): WeatherCondition?

    fun getWeatherConditions(): List<WeatherCondition>

    fun getWeatherConditionsFlow(): Flow<List<WeatherCondition>>

}

