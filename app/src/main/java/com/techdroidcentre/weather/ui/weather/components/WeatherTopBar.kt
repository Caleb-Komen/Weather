package com.techdroidcentre.weather.ui.weather.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun WeatherTopBar(
    onShowUnitsDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        TopAppBar(
            title = { Text(text = "Weather") },
            actions = {
                IconButton(onClick = onShowUnitsDialog) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }
        )
    }
}