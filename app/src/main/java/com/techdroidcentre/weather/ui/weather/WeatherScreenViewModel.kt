package com.techdroidcentre.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techdroidcentre.weather.core.Result
import com.techdroidcentre.weather.core.usecases.GetWeatherDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(WeatherScreenUiState())
    val uiState: StateFlow<WeatherScreenUiState> get() = _uiState

    init {
        processEvent(WeatherScreenEvent.GetWeatherData(-1.29f, 36.87f))
    }

    fun processEvent(event: WeatherScreenEvent) {
        when (event) {
            is WeatherScreenEvent.GetWeatherData -> {
                getWeatherData(event.latitude, event.longitude)
            }

        }
    }

    private fun getWeatherData(latitude: Float, longitude: Float) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getWeatherDataUseCase(latitude, longitude, "standard").collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(weather = result.data, error = "", isLoading = false)
                    }
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.message, isLoading = false)
                    }
                }
            }
        }
    }
}
