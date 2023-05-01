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
    val windSpeed: Float,
    val weatherInfo: List<WeatherInfo>
)

data class HourlyWeather(
    val time: String,
    val temperature: Float,
    val weatherInfo: List<WeatherInfo>
)

data class DailyWeather(
    val time: String,
    val temperatureInfo: TemperatureInfo,
    val weatherInfo: List<WeatherInfo>
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
