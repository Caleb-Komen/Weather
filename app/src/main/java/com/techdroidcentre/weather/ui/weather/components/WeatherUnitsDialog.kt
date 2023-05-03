package com.techdroidcentre.weather.ui.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.techdroidcentre.weather.core.model.Units
import java.util.Locale

@Composable
fun WeatherUnitsDialog(
    units: String,
    onSubmitOption: (String) -> Unit,
    onCloseDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onCloseDialog
    ) {
        val radioOptions = Units.values().map { it.value }
        var selectedOption by remember { mutableStateOf(units) }

        Box(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Choose Unit of measurement",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                radioOptions.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = option == selectedOption,
                            onClick = {
                                selectedOption = option
                            }
                        )
                        Text(text = option.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                    }
                }
            }
            Button(
                onClick = {
                    onSubmitOption(selectedOption)
                    onCloseDialog()
                },
                modifier = Modifier.align(Alignment.BottomEnd)
                    .background(Color.Transparent)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(text = "OK",)
            }
        }
    }
}