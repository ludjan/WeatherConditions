package com.example.weatherconditions.model.weatherCondition

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherCondition(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    val name: String,
    val conditionType: ConditionType,
    val conditionOperator: ConditionOperator,
    val conditionValue: Double,
    var conditionResult: Boolean = false
)
