package com.techdroidcentre.weather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techdroidcentre.weather.ui.weather.UIComponentState
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
        WeatherScreen(
            viewModel = viewModel,
            modifier = Modifier.padding(it)
        )
    }
}
