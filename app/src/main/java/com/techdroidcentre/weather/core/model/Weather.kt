package com.techdroidcentre.weather.core.model

data class Weather(
    val currentWeather: CurrentWeather,
    val hourlyWeather: List<HourlyWeather>,
    val dailyWeather: List<DailyWeather>
)

data class CurrentWeather(
    val temperature: Float,
    val feelsLike: Float,
    val humidity: Int,
    val windSpeed: Int,
    val weatherInfo: WeatherInfo
)

data class HourlyWeather(
    val time: Long,
    val temperature: Float,
    val weatherInfo: WeatherInfo
)

data class DailyWeather(
    val temperatureInfo: TemperatureInfo,
    val weatherInfo: WeatherInfo
)

data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class TemperatureInfo(
    val min: Float,
    val max: Float,
)
