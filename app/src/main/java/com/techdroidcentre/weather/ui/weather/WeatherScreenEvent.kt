package com.techdroidcentre.weather.ui.weather

import com.techdroidcentre.weather.core.model.UIComponentState

sealed interface WeatherScreenEvent{
    object GetWeatherData: WeatherScreenEvent

    data class UpdateWeatherUnitsDialogState(
        val uiComponentState: UIComponentState
    ): WeatherScreenEvent

    data class UpdateUnits(val units: String): WeatherScreenEvent

    object RemoveHeadFromQueue: WeatherScreenEvent

    data class UpdateLocationName(val locatioName: String): WeatherScreenEvent
}