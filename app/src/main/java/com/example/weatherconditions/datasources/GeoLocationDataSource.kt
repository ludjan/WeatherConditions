package com.example.weatherconditions.datasources

import com.example.weatherconditions.model.GeoLocation

class GeoLocationDataSource {
    suspend fun getLastKnownCoordinates() : GeoLocation {
        return GeoLocation(59.908160, 10.778775)
    }
}