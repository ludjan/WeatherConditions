package com.example.weatherconditions.repositories

import com.example.weatherconditions.datasources.GeoLocationDataSource
import com.example.weatherconditions.model.GeoLocation
import com.example.weatherconditions.model.weatherCondition.WeatherCondition
import com.example.weatherconditions.repositories.WeatherConditionRepository
import com.example.weatherconditions.room.WeatherConditionDao
import kotlinx.coroutines.flow.Flow

class WeatherConditionRepositoryImpl (
    private val dao: WeatherConditionDao,
    private val geoLocationDataSource: GeoLocationDataSource
) : WeatherConditionRepository {

    private var _geoLocation : GeoLocation? = null // for caching GeoLocation

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