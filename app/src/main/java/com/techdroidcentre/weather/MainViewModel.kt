package com.techdroidcentre.weather

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
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
}

data class MainUiState(
    val isPermissionGranted: Boolean = false,
    val isLocationEnabled: Boolean = false
)