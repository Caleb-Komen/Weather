package com.techdroidcentre.weather.ui.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techdroidcentre.weather.core.Result
import com.techdroidcentre.weather.core.SettingsRepository
import com.techdroidcentre.weather.core.model.DefaultLocation
import com.techdroidcentre.weather.core.model.UIComponent
import com.techdroidcentre.weather.core.usecases.GetWeatherDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingQueue
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
            settingsRepository.getUnits().combine(settingsRepository.getDefaultLocation()) { units, defaultLocation ->
                listOf(units, defaultLocation)
            }.collect { result ->
                _uiState.update {
                    it.copy(units = result[0] as String, location = result[1] as DefaultLocation)
                }
                processEvent(WeatherScreenEvent.GetWeatherData)
            }
        }
    }

    fun processEvent(event: WeatherScreenEvent) {
        when (event) {
            is WeatherScreenEvent.GetWeatherData -> {
                getWeatherData(uiState.value.location.latitude, uiState.value.location.longitude)
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

            WeatherScreenEvent.RemoveHeadFromQueue -> removeHeadMessage()

            is WeatherScreenEvent.UpdateLocationName -> {
                _uiState.update {
                    it.copy(locationName = event.locatioName)
                }
            }
        }
    }

    private fun getWeatherData(latitude: Float, longitude: Float) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getWeatherDataUseCase(latitude, longitude, uiState.value.units).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(weather = result.data, isLoading = false)
                        }
                    }
                    is Result.Error -> {
                        appendToMessageQueue(result.uiComponent)
                    }
                }
            }
        }
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        try {
            val queue = LinkedBlockingQueue(_uiState.value.errorQueue)
            queue.add(uiComponent)
            _uiState.update {
                it.copy(errorQueue = queue, isLoading = false)
            }
        } catch (ex: Exception) {
            Log.d(TAG, "No more space is currently available.")
        }
    }

    private fun removeHeadMessage() {
        try {
            val queue = LinkedBlockingQueue(_uiState.value.errorQueue)
            queue.remove()
            _uiState.update {
                it.copy(errorQueue = queue)
            }
        } catch (ex: Exception) {
            Log.d(TAG, "No more elements to remove from queue.")
        }
    }

    companion object {
        const val TAG = "WeatherScreenViewModel"
    }
}
