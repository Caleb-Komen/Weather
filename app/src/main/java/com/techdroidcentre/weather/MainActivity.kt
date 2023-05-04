package com.techdroidcentre.weather

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.techdroidcentre.weather.ui.WeatherApp
import com.techdroidcentre.weather.ui.theme.WeatherTheme
import com.techdroidcentre.weather.util.PermissionAction
import com.techdroidcentre.weather.util.PermissionDialog
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()

        setContent {
            val state by viewModel.uiState.collectAsState()

            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PermissionDialog(
                        LocalContext.current,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) { permissionAction ->
                        when (permissionAction) {
                            PermissionAction.PermissionGranted -> {
                                viewModel.setPermission(true)
                            }
                            PermissionAction.PermissionDenied -> {
                                viewModel.setPermission(false)
                            }
                        }
                    }

                    if (state.isPermissionGranted) {
                        WeatherApp()
                    }
                }
            }
        }
    }
}
