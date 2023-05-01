package com.techdroidcentre.weather.ui.weather

sealed interface WeatherScreenEvent{
    data class GetWeatherData(
        val latitude: Float,
        val longitude: Float
    ): WeatherScreenEvent
}