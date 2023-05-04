package com.techdroidcentre.weather.util

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority

fun createLocationRequest(
    activity: Activity,
    locationRequestLauncher: ActivityResultLauncher<IntentSenderRequest>,
    onLocationRequestSuccessful: () -> Unit
) {
    val request = LocationRequest.Builder(60000L).apply {
        setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
    }.build()

    val settings = LocationSettingsRequest.Builder()
        .addLocationRequest(request)
        .build()

    val task = LocationServices.getSettingsClient(activity)
        .checkLocationSettings(settings)

    task.addOnCompleteListener { response ->
        try {
            val result = response.getResult(ApiException::class.java)
            val hasLocationAccess = result.locationSettingsStates?.isLocationUsable == true
            if (hasLocationAccess) {
                onLocationRequestSuccessful()
            }
        } catch (ex: ApiException) {
            when (ex.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    val resolvable = ex as ResolvableApiException
                    val intentSender = IntentSenderRequest.Builder(resolvable.resolution).build()
                    locationRequestLauncher.launch(intentSender)
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    // Do nothing, location settings can't be changed
                }
            }
        }
    }
}
