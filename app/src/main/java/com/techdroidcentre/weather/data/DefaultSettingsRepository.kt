package com.techdroidcentre.weather.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.techdroidcentre.weather.core.SettingsRepository
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
}