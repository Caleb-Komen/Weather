package com.techdroidcentre.weather.ui.weather.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GenericDialog(
    title: String,
    onCloseDialog: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onCloseDialog,
        title = {
            Text(
                text = title
            )
        },
        text = {
            if (description != null) {
                Text(text = description)
            }
        },
        confirmButton = {
            Button(onClick = onCloseDialog) {
                Text(text = "OK")
            }
        }
    )
}