package com.techdroidcentre.weather.ui.weather

import com.techdroidcentre.weather.core.model.Weather

data class WeatherScreenUiState(
    val weather: Weather? = null,
    val error: String = "",
    val isLoading: Boolean = false
)