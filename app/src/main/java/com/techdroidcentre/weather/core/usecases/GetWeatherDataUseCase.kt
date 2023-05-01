package com.techdroidcentre.weather.core.usecases

import com.techdroidcentre.weather.core.Result
import com.techdroidcentre.weather.core.WeatherRepository
import com.techdroidcentre.weather.core.model.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        latitude: Float,
        longitude: Float,
        units: String,
    ): Flow<Result<Weather>> {
        return weatherRepository.getWeatherData(latitude, longitude, units)
    }
}