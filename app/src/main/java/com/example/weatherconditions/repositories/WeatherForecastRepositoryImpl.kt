package com.example.weatherconditions.repositories

import com.example.weatherconditions.datasources.LocationForecast
import com.example.weatherconditions.datasources.LocationForecastDataSource

class WeatherForecastRepositoryImpl (
    private val locationForecastDataSource: LocationForecastDataSource
) : WeatherForecastRepository {

    private var _locationForecast : LocationForecast? = null // for caching locationForecast

    override suspend fun getWeatherForecast(lat: Double, lon: Double): LocationForecast {
        if (_locationForecast == null) {
            _locationForecast = locationForecastDataSource.getLocationForecast(lat, lon)
        }
        return _locationForecast!!
    }
}