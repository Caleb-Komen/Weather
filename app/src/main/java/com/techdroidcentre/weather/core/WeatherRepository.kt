package com.techdroidcentre.weather.core

import com.techdroidcentre.weather.core.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherData(
        latitude: Float,
        longitude: Float,
        units: String
    ): Flow<Result<Weather>>
}