package com.techdroidcentre.weather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techdroidcentre.weather.core.model.UIComponentState
import com.techdroidcentre.weather.ui.weather.WeatherScreen
import com.techdroidcentre.weather.ui.weather.WeatherScreenEvent
import com.techdroidcentre.weather.ui.weather.WeatherScreenViewModel
import com.techdroidcentre.weather.ui.weather.components.WeatherTopBar

@ExperimentalMaterial3Api
@Composable
fun WeatherApp() {
    val viewModel: WeatherScreenViewModel = viewModel()
    Scaffold(
        topBar = {
            WeatherTopBar(onShowUnitsDialog = {
                viewModel.processEvent(WeatherScreenEvent.UpdateWeatherUnitsDialogState(
                    UIComponentState.Show
                ))
            })
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()

        WeatherScreen(
            uiState = uiState,
            event = viewModel::processEvent,
            modifier = Modifier.padding(it)
        )
    }
}
