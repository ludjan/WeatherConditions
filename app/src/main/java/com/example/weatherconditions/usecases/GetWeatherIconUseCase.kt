package com.example.weatherconditions.usecases

import com.example.weatherconditions.R

object GetWeatherIconUseCase {
    fun getWeatherIcon(img_id: Int): Int {
        return when (img_id) {
            // add more icons and keys as icon set grows
            1 -> R.drawable.ic_sun_icon
            else -> R.drawable.ic_launcher_foreground
        }
    }
}