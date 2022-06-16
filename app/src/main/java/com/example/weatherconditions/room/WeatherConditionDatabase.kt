package com.example.weatherconditions.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherconditions.model.watherCondition.WeatherCondition

@Database(
    entities = [WeatherCondition::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherConditionDatabase : RoomDatabase() {

    // app module provides the singleton pattern to keep this as only one instance
    abstract val dao : WeatherConditionDao
}