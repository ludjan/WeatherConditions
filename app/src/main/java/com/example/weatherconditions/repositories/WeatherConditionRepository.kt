package com.example.weatherconditions.repositories

import com.example.weatherconditions.datasources.LocationForecast
import com.example.weatherconditions.model.GeoLocation
import com.example.weatherconditions.model.weatherCondition.WeatherCondition
import kotlinx.coroutines.flow.Flow

// Repository should have the access to all Datasources, and decide what to forward to viewModels

interface WeatherConditionRepository {

    suspend fun getGeoLocation(): GeoLocation

    suspend fun insertWeatherCondition(weatherCondition: WeatherCondition)

    suspend fun deleteWeatherCondition(weatherCondition: WeatherCondition)

    suspend fun getWeatherConditionById(id: Int): WeatherCondition?

    fun getWeatherConditions(): List<WeatherCondition>

    fun getWeatherConditionsFlow(): Flow<List<WeatherCondition>>

}

