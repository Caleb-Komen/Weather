package com.techdroidcentre.weather.ui.weather.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalMaterial3Api
@Composable
fun WeatherTopBar(
    onShowUnitsDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "Weather") },
        actions = {
            IconButton(onClick = onShowUnitsDialog) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
        },
        modifier = modifier
    )
}