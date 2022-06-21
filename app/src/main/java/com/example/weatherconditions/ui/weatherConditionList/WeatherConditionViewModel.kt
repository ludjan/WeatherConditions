package com.example.weatherconditions.ui.weatherConditionList

import androidx.lifecycle.*
import com.example.weatherconditions.datasources.LocationForecast
import com.example.weatherconditions.repositories.WeatherConditionRepository
import com.example.weatherconditions.model.GeoLocation
import com.example.weatherconditions.model.WeatherConditionResult
import com.example.weatherconditions.model.weatherCondition.ConditionOperator
import com.example.weatherconditions.model.weatherCondition.ConditionType
import com.example.weatherconditions.model.weatherCondition.WeatherCondition
import com.example.weatherconditions.repositories.WeatherForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherConditionViewModel @Inject constructor(
    private val weatherConditionRepository: WeatherConditionRepository,
    private val weatherForecastRepository: WeatherForecastRepository
): ViewModel() {

    private val _weatherConditionsResult = MutableLiveData<List<WeatherConditionResult>>()

    fun getWeatherConditionResults(): LiveData<List<WeatherConditionResult>> {
        return _weatherConditionsResult
    }

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    fun setWeatherConditionResults() {

        var weatherConditions: List<WeatherCondition>
        var geoLocation: GeoLocation
        var locationForecast: LocationForecast

        viewModelScope.launch {

            // get weatherConditions async
            val weatherConditionsDeferred = CoroutineScope(Dispatchers.IO).async {
                weatherConditionRepository.getWeatherConditions()
            }
            weatherConditions = weatherConditionsDeferred.await()

            // get geoLocation async
            val geoLocationDeferred = CoroutineScope(Dispatchers.IO).async {
                weatherConditionRepository.getGeoLocation()
            }
            geoLocation = geoLocationDeferred.await()

            // get locationForecast async
            val locationForecastDeferred = CoroutineScope(Dispatchers.IO).async {
                weatherForecastRepository.getWeatherForecast(geoLocation.lat, geoLocation.lon)
            }
            locationForecast = locationForecastDeferred.await()

            val windStrength = locationForecast.properties?.timeseries?.get(0)?.data?.instant?.details?.wind_speed
            val airTemp = locationForecast.properties?.timeseries?.get(0)?.data?.instant?.details?.air_temperature

            val list = mutableListOf<WeatherConditionResult>()
            weatherConditions.forEach {

                // check result
                var result = false

                if (windStrength != null && airTemp != null) {
                    result = when (it.conditionType) {

                        ConditionType.TEMPERATURE ->
                        when (it.conditionOperator) {
                            ConditionOperator.BIGGER_THAN -> airTemp > it.conditionValue
                            ConditionOperator.SMALLER_THAN -> airTemp < it.conditionValue
                        }

                        ConditionType.WIND_STRENGTH ->
                        when (it.conditionOperator) {
                            ConditionOperator.BIGGER_THAN -> airTemp > it.conditionValue
                            ConditionOperator.SMALLER_THAN -> airTemp < it.conditionValue
                        }
                    }
                }
                list.add(WeatherConditionResult(it, result))
            } // end of foreach
            _weatherConditionsResult.postValue(list)
        }
    }
}
