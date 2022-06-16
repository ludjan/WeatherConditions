package com.example.weatherconditions.ui.weatherConditionList

import com.example.weatherconditions.model.watherCondition.WeatherCondition

// should keep an object for each action which user can take from this screen
sealed class WeatherConditionListEvent {
    data class OnWeatherConditionClick(val wc: WeatherCondition): WeatherConditionListEvent()
    object OnAddWeatherConditionClick: WeatherConditionListEvent()
}
