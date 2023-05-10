package com.techdroidcentre.weather.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.techdroidcentre.weather.core.SettingsRepository
import com.techdroidcentre.weather.core.model.DefaultLocation
import com.techdroidcentre.weather.core.model.Units
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.datastore by preferencesDataStore(name = "settings")

class DefaultSettingsRepository @Inject constructor(
    @ApplicationContext val context: Context
): SettingsRepository {
    private val unitsKey = stringPreferencesKey("units")
    private val defaultLocationKey = stringPreferencesKey("defaultLocation")

    override suspend fun setUnits(units: String) {
        context.datastore.edit { settings ->
            settings[unitsKey] = units
        }
    }

    override fun getUnits(): Flow<String> {
        return context.datastore.data.map { preferences ->
            preferences[unitsKey] ?: Units.METRIC.value
        }
    }

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        context.datastore.edit { settings ->
            settings[defaultLocationKey] = "${defaultLocation.latitude},${defaultLocation.longitude}"
        }
    }

    override fun getDefaultLocation(): Flow<DefaultLocation> {
        return context.datastore.data.map { preferences ->
            val latLong = (preferences[defaultLocationKey] ?: "-1.29,36.87").split(",").map { it.toFloat() }
            DefaultLocation(latLong[0], latLong[1])
        }
    }
}