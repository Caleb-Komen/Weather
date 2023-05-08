package com.techdroidcentre.weather.ui.weather

import com.techdroidcentre.weather.core.model.UIComponent
import com.techdroidcentre.weather.core.model.Weather
import java.util.Queue
import java.util.concurrent.LinkedBlockingQueue

data class WeatherScreenUiState(
    val weather: Weather? = null,
    val location: DefaultLocation = DefaultLocation(-1.29f, 36.87f),
    val errorQueue: Queue<UIComponent> = LinkedBlockingQueue(mutableListOf()),
    val units: String = "",
    val isLoading: Boolean = false,
    val weatherUnitsDialogState: UIComponentState = UIComponentState.Hide,
)

data class DefaultLocation(
    val latitude: Float,
    val longitude: Float
)

sealed class UIComponentState {
    object Show: UIComponentState()

    object Hide: UIComponentState()
}