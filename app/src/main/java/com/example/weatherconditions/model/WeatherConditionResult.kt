package com.example.weatherconditions.model

import com.example.weatherconditions.model.weatherCondition.WeatherCondition

data class WeatherConditionResult (val weatherCondition: WeatherCondition, val result: Boolean)