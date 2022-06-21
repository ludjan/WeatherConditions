package com.example.weatherconditions.di

import android.app.Application
import androidx.room.Room
import com.example.weatherconditions.datasources.LocationForecastDataSource
import com.example.weatherconditions.repositories.WeatherConditionRepository
import com.example.weatherconditions.repositories.WeatherConditionRepositoryImpl
import com.example.weatherconditions.datasources.GeoLocationDataSource
import com.example.weatherconditions.repositories.WeatherForecastRepository
import com.example.weatherconditions.repositories.WeatherForecastRepositoryImpl
import com.example.weatherconditions.room.WeatherConditionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// define how the dependencies should be created

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    // dagger calls this function automatically
    fun provideWeatherConditionDatabase(app: Application): WeatherConditionDatabase {
        return Room.databaseBuilder(
            app,
            WeatherConditionDatabase::class.java,
            "weathercondition_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherConditionRepository(
        db: WeatherConditionDatabase,
        geo: GeoLocationDataSource
    ): WeatherConditionRepository {
        return WeatherConditionRepositoryImpl(db.dao, geo)
    }

    @Provides
    @Singleton
    fun provideWeatherForecastRepository(
        location: LocationForecastDataSource
    ): WeatherForecastRepository {
        return WeatherForecastRepositoryImpl(location)
    }

    @Provides
    @Singleton
    fun provideGeoLocationDataSource(): GeoLocationDataSource {
        return GeoLocationDataSource()
    }

    @Provides
    @Singleton
    fun provideLocationForecastDataSource(): LocationForecastDataSource {
        return LocationForecastDataSource()
    }
}