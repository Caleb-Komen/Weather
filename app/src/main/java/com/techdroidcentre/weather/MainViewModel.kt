package com.techdroidcentre.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techdroidcentre.weather.core.SettingsRepository
import com.techdroidcentre.weather.core.model.DefaultLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    fun setPermissionGranted(isGranted: Boolean) {
        _uiState.update {
            it.copy(isPermissionGranted = isGranted)
        }
    }

    fun setLocationEnabled(isEnabled: Boolean) {
        _uiState.update {
            it.copy(isLocationEnabled = isEnabled)
        }
    }

    fun setDefaultLocation(defaultLocation: DefaultLocation) {
        viewModelScope.launch {
            settingsRepository.setDefaultLocation(defaultLocation)
        }
    }
}

data class MainUiState(
    val isPermissionGranted: Boolean = false,
    val isLocationEnabled: Boolean = false
)