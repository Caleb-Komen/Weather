package com.techdroidcentre.weather.ui.weather

import com.techdroidcentre.weather.core.model.DefaultLocation
import com.techdroidcentre.weather.core.model.UIComponent
import com.techdroidcentre.weather.core.model.UIComponentState
import com.techdroidcentre.weather.core.model.Weather
import java.util.Queue
import java.util.concurrent.LinkedBlockingQueue

data class WeatherScreenUiState(
    val weather: Weather? = null,
    val location: DefaultLocation = DefaultLocation(0.0f, 0.0f),
    val errorQueue: Queue<UIComponent> = LinkedBlockingQueue(mutableListOf()),
    val units: String = "",
    val isLoading: Boolean = false,
    val weatherUnitsDialogState: UIComponentState = UIComponentState.Hide,
)
