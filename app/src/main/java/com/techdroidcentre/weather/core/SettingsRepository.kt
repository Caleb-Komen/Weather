package com.techdroidcentre.weather.core

import com.techdroidcentre.weather.core.model.DefaultLocation
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setUnits(units: String)

    fun getUnits(): Flow<String>

    suspend fun setDefaultLocation(defaultLocation: DefaultLocation)

    fun getDefaultLocation(): Flow<DefaultLocation>
}