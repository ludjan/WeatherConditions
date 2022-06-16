package com.example.weatherconditions

import android.content.Context
import com.example.weatherconditions.datasources.GeoLocationDataSource
import com.example.weatherconditions.model.GeoLocation
import com.example.weatherconditions.model.watherCondition.WeatherCondition
import com.example.weatherconditions.room.WeatherConditionDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class WeatherConditionRepositoryImpl (
    private val dao: WeatherConditionDao,
    private val geoLocationDataSource: GeoLocationDataSource,
    private val locationForecastDataSource: LocationForecastDataSource
) : WeatherConditionRepository {

    private var _geoLocation : GeoLocation? = null // for caching GeoLocation
    private var _locationForecast : LocationForecast? = null // for caching locationForecast

    override suspend fun getLocationForecast(): LocationForecast {
        if (_locationForecast == null) {
            _locationForecast = locationForecastDataSource.getLocationForecast(_geoLocation!!.lat, _geoLocation!!.lon)
        }
        return _locationForecast!!
    }

    override suspend fun getGeoLocation(): GeoLocation {
        if (_geoLocation == null) {
            _geoLocation = geoLocationDataSource.getLastKnownCoordinates()
        }
        return _geoLocation!!
    }

    override suspend fun insertWeatherCondition(weatherCondition: WeatherCondition) {
        dao.insertWeatherConditions(weatherCondition)
    }

    override suspend fun deleteWeatherCondition(weatherCondition: WeatherCondition) {
        dao.deleteWeatherCondition(weatherCondition)
    }

    override suspend fun getWeatherConditionById(id: Int): WeatherCondition? {
        return dao.getWeatherConditionById(id)
    }

    override fun getWeatherConditions(): List<WeatherCondition> {
        return dao.getWeatherConditions()
    }

    override fun getWeatherConditionsFlow(): Flow<List<WeatherCondition>> {
        return dao.getWeatherConditionsFlow()
    }
}