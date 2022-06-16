package com.example.weatherconditions.datasources;

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class LocationForecastDataSource {

    private val tag = "LocationForecastDataSource"

    private var locationForecast: LocationForecast? = null

    suspend fun getLocationForecast(lat: Double, lon: Double) : LocationForecast? {
        // create gson object for deserializing
        val gson = Gson()

        // Format longitude and latitude from func params into query
        val query = "https://in2000-apiproxy.ifi.uio.no/weatherapi/locationforecast/2.0/compact?lat=$lat&lon=$lon"
        Log.d(tag, query)
//        val baseProxyURL = "https://in2000-apiproxy.ifi.uio.no/weatherapi/locationforecast/2.0/compact?"

        // Create the response object we are trying to fill with data
        var response : LocationForecast? = null

        try {
            // Use Fuel to get the response from the API
            val responseString = Fuel.get(query).awaitString()

            // Deserialize response to LocationForecast objects
            response = gson.fromJson(responseString, LocationForecast::class.java)

        } catch (e: JsonSyntaxException) {
            Log.d("getFromLocationForecastAPI", "JsonSyntaxException: ${e.message}")
        } catch (e: FuelError) {
            Log.d("getFromLocationForecastAPI", "FuelError: ${e.message}")
        } catch (e: Exception) {
            Log.d("getFromLocationForecastAPI", "Exception: ${e.message}")
        }

        if (response == null) {
            Log.d("getFromLocationForecastAPI", "Response was null")
        } else {
            Log.d(tag, "$response")
        }

        return response
    }

    suspend fun getAirTemperature() : Double {
       if (locationForecast == null) {
           getFromLocationForecastAPI(1.1, 2.2)
       } else {
           Log.d(tag, "No need to get data from API")
       }
        return locationForecast?.properties?.timeseries?.get(0)?.data?.instant?.details?.air_temperature as Double
    }

    /**
     * This function takes coordinates as parameter and returns the forecast for this position.
     */
    suspend fun getFromLocationForecastAPI(latt: Double, lonn: Double) {

        val lat = 59.908171
        val lon = 10.779239

        // create gson object for deserializing
        val gson = Gson()

        // Format longitude and latitude from func params into query
        val query = "https://in2000-apiproxy.ifi.uio.no/weatherapi/locationforecast/2.0/compact?lat=$lat&lon=$lon"
        Log.d(tag, query)
//        val baseProxyURL = "https://in2000-apiproxy.ifi.uio.no/weatherapi/locationforecast/2.0/compact?"

        // Create the response object we are trying to fill with data
        var response : LocationForecast? = null

        try {
            // Use Fuel to get the response from the API
            val responseString = Fuel.get(query).awaitString()

            // Deserialize response to LocationForecast objects
            response = gson.fromJson(responseString, LocationForecast::class.java)

        } catch (e: JsonSyntaxException) {
            Log.d("getFromLocationForecastAPI", "JsonSyntaxException: ${e.message}")
        } catch (e: FuelError) {
            Log.d("getFromLocationForecastAPI", "FuelError: ${e.message}")
        } catch (e: Exception) {
            Log.d("getFromLocationForecastAPI", "Exception: ${e.message}")
        }

        if (response == null) {
            Log.d("getFromLocationForecastAPI", "Response was null")
        } else {
            Log.d(tag, "$response")
        }

        locationForecast = response
    }
}

// result generated from /json

data class LocationForecast(val type: String?, val geometry: Geometry?, val properties: Properties?)

data class Data(val instant: Instant?, val next_12_hours: Next_12_hours?, val next_1_hours: Next_1_hours?, val next_6_hours: Next_6_hours?)

data class Details(val air_pressure_at_sea_level: Number?, val air_temperature: Double?, val cloud_area_fraction: Number?, val relative_humidity: Number?, val wind_from_direction: Number?, val wind_speed: Number?)

data class Geometry(val type: String?, val coordinates: List<Number>?)

data class Instant(val details: Details?)

data class Meta(val updated_at: String?, val units: Units?)

data class Next_12_hours(val summary: Summary?)

data class Next_1_hours(val summary: Summary?, val details: Details?)

data class Next_6_hours(val summary: Summary?, val details: Details?)

data class Properties(val meta: Meta?, val timeseries: List<TimeSeries>?)

data class Summary(val symbol_code: String?)

data class TimeSeries(val time: String?, val data: Data?)

data class Units(val air_pressure_at_sea_level: String?, val air_temperature: String?, val cloud_area_fraction: String?, val precipitation_amount: String?, val relative_humidity: String?, val wind_from_direction: String?, val wind_speed: String?)
