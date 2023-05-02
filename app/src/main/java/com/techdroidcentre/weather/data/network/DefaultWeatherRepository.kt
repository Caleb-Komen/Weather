package com.techdroidcentre.weather.data.network

import com.techdroidcentre.weather.core.Result
import com.techdroidcentre.weather.core.WeatherRepository
import com.techdroidcentre.weather.core.model.Units
import com.techdroidcentre.weather.core.model.Weather
import com.techdroidcentre.weather.data.network.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val apiService: WeatherApiService
): WeatherRepository {
    override fun getWeatherData(
        latitude: Float,
        longitude: Float,
        units: String
    ): Flow<Result<Weather>> = flow {
        try {
            val weather: Weather = apiService.getWeatherData(
                latitude, longitude, units
            ).toModel(units = Units.valueOf(units.uppercase()))
            emit(Result.Success(weather))
        } catch (ex: Exception) {
            emit(Result.Error(ex.message ?: "Unknown Error"))
        }
    }
}