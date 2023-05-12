package com.techdroidcentre.weather.util

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import java.util.Locale

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
                    // No op
                }
            }
        }
    }
}

fun Context.getLocationName(longitude: Double, latitude: Double, onAddressReceived: (Address) -> Unit) {
    val geoCoder = Geocoder(this, Locale.getDefault())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geoCoder.getFromLocation(latitude, longitude, 1) { addresses ->
            if (addresses.isNotEmpty()) {
                onAddressReceived(addresses[0])
            }
        }
    } else {
        val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
        if (addresses?.isNotEmpty() == true) {
            onAddressReceived(addresses[0])
        }
    }
}
