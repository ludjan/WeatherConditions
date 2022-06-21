package com.example.weatherconditions.repositories

import com.example.weatherconditions.datasources.LocationForecast
import com.example.weatherconditions.model.GeoLocation
import com.example.weatherconditions.model.weatherCondition.WeatherCondition
import kotlinx.coroutines.flow.Flow

// Repository should have the access to all relevant weather Datasources,
// and decide what to forward to viewModels

interface WeatherForecastRepository {

    suspend fun getWeatherForecast(lat: Double, lon: Double): LocationForecast

}

