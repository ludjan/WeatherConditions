package com.example.weatherconditions.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherconditions.model.weatherCondition.WeatherCondition

@Database(
    entities = [WeatherCondition::class],
    version = 3,
    exportSchema = false
)
abstract class WeatherConditionDatabase : RoomDatabase() {

    // app module provides the singleton pattern to keep this as only one instance
    abstract val dao : WeatherConditionDao

}