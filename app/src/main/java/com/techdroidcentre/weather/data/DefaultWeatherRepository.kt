package com.techdroidcentre.weather.data

import com.techdroidcentre.weather.core.Result
import com.techdroidcentre.weather.core.WeatherRepository
import com.techdroidcentre.weather.core.model.UIComponent
import com.techdroidcentre.weather.core.model.Units
import com.techdroidcentre.weather.core.model.Weather
import com.techdroidcentre.weather.data.network.WeatherApiService
import com.techdroidcentre.weather.data.network.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
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
            when (ex) {
                is IOException -> {
                    emit(Result.Error(UIComponent.Banner("Network Error")))
                }
                else -> {
                    emit(Result.Error(UIComponent.Dialog("Error", ex.message ?: "Unknown Error")))
                }
            }
        }
    }
}