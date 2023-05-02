package com.techdroidcentre.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techdroidcentre.weather.core.Result
import com.techdroidcentre.weather.core.SettingsRepository
import com.techdroidcentre.weather.core.usecases.GetWeatherDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val settingsRepository: SettingsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(WeatherScreenUiState())
    val uiState: StateFlow<WeatherScreenUiState> get() = _uiState

    init {
        viewModelScope.launch {
            settingsRepository.getUnits().collect { units ->
                _uiState.update {
                    it.copy(units = units)
                }
            }
        }
        processEvent(WeatherScreenEvent.GetWeatherData(-1.29f, 36.87f))
    }

    fun processEvent(event: WeatherScreenEvent) {
        when (event) {
            is WeatherScreenEvent.GetWeatherData -> {
                getWeatherData(event.latitude, event.longitude)
            }

            is WeatherScreenEvent.UpdateWeatherUnitsDialogState -> {
                _uiState.update {
                    it.copy(weatherUnitsDialogState = event.uiComponentState)
                }
            }

            is WeatherScreenEvent.UpdateUnits -> {
                _uiState.update {
                    it.copy(units = event.units)
                }
                viewModelScope.launch {
                    settingsRepository.setUnits(event.units)
                }
            }
        }
    }

    private fun getWeatherData(latitude: Float, longitude: Float) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getWeatherDataUseCase(latitude, longitude, _uiState.value.units).collect { result ->
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
