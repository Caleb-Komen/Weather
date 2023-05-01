package com.techdroidcentre.weather.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val currentWeatherResponse: CurrentWeatherResponse,
    @SerialName("hourly")
    val hourlyWeatherResponse: List<HourlyWeatherResponse>,
    @SerialName("daily")
    val dailyWeatherResponse: List<DailyWeatherResponse>
)

@Serializable
data class CurrentWeatherResponse(
    @SerialName("temp")
    val temperature: Float,
    @SerialName("feels_like")
    val feelsLike: Float,
    val humidity: Int,
    @SerialName("wind_speed")
    val windSpeed: Float,
    @SerialName("weather")
    val weatherInfoResponse: List<WeatherInfoResponse>
)

@Serializable
data class HourlyWeatherResponse(
    @SerialName("dt")
    val time: Long,
    @SerialName("temp")
    val temperature: Float,
    @SerialName("weather")
    val weatherInfoResponse: List<WeatherInfoResponse>
)

@Serializable
data class DailyWeatherResponse(
    @SerialName("dt")
    val time: Long,
    @SerialName("temp")
    val temperatureInfoResponse: TemperatureInfoResponse,
    @SerialName("weather")
    val weatherInfoResponse: List<WeatherInfoResponse>
)

@Serializable
data class WeatherInfoResponse(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class TemperatureInfoResponse(
    val min: Float,
    val max: Float,
)