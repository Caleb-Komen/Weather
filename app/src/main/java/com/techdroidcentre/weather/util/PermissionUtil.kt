package com.techdroidcentre.weather.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun PermissionDialog(
    context: Context,
    permission: String,
    permissionAction: (PermissionAction) -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(true) }

    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
        permissionAction(PermissionAction.PermissionGranted)
        return
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            permissionAction(PermissionAction.PermissionGranted)
        } else {
            permissionAction(PermissionAction.PermissionDenied)
        }
    }

    val activity = context as Activity?
    val shouldShowPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)

    if (shouldShowPermissionRationale) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    permissionAction(PermissionAction.PermissionDenied)
                    showDialog = false
                },
                title = {
                    Text(text = "Action Required")
                },
                text = {
                    Text(text = "The app need location permission in order to get weather information of your location")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            permissionLauncher.launch(permission)
                            showDialog = false
                        }
                    ) {
                        Text(text = "Grant")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            permissionAction(PermissionAction.PermissionDenied)
                            showDialog = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    } else {
        SideEffect {
            permissionLauncher.launch(permission)
        }
    }
}

sealed class PermissionAction {
    object PermissionGranted: PermissionAction()
    object PermissionDenied: PermissionAction()
}