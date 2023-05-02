package com.techdroidcentre.weather.core.model

data class Weather(
    val currentWeather: CurrentWeather,
    val hourlyWeather: List<HourlyWeather>,
    val dailyWeather: List<DailyWeather>
)

data class CurrentWeather(
    val temperature: String,
    val feelsLike: String,
    val humidity: String,
    val windSpeed: String,
    val weatherInfo: List<WeatherInfo>
)

data class HourlyWeather(
    val time: String,
    val temperature: String,
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
    val min: String,
    val max: String,
)
