package com.techdroidcentre.weather.core

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setUnits(units: String)

    fun getUnits(): Flow<String>
}