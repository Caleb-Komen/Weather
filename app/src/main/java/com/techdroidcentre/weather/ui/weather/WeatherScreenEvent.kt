package com.techdroidcentre.weather.ui.weather

sealed interface WeatherScreenEvent{
    data class GetWeatherData(
        val latitude: Float,
        val longitude: Float
    ): WeatherScreenEvent

    data class UpdateWeatherUnitsDialogState(
        val uiComponentState: UIComponentState
    ): WeatherScreenEvent

    data class UpdateUnits(val units: String): WeatherScreenEvent
}