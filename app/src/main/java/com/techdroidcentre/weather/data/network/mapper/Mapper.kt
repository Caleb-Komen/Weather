package com.techdroidcentre.weather.data.network.mapper

import com.techdroidcentre.weather.core.model.CurrentWeather
import com.techdroidcentre.weather.core.model.DailyWeather
import com.techdroidcentre.weather.core.model.HourlyWeather
import com.techdroidcentre.weather.core.model.TemperatureInfo
import com.techdroidcentre.weather.core.model.Weather
import com.techdroidcentre.weather.core.model.WeatherInfo
import com.techdroidcentre.weather.data.network.model.CurrentWeatherResponse
import com.techdroidcentre.weather.data.network.model.DailyWeatherResponse
import com.techdroidcentre.weather.data.network.model.HourlyWeatherResponse
import com.techdroidcentre.weather.data.network.model.TemperatureInfoResponse
import com.techdroidcentre.weather.data.network.model.WeatherInfoResponse
import com.techdroidcentre.weather.data.network.model.WeatherResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val ICON_URL = "https://openweathermap.org/img/wn/"

fun WeatherResponse.toModel(): Weather {
    return Weather(
        currentWeather = currentWeatherResponse.toModel(),
        hourlyWeather = hourlyWeatherResponse.map { it.toModel() },
        dailyWeather = dailyWeatherResponse.map { it.toModel() }
    )
}

fun CurrentWeatherResponse.toModel(): CurrentWeather {
    return CurrentWeather(
        temperature = temperature,
        feelsLike = feelsLike,
        humidity = humidity,
        windSpeed = windSpeed,
        weatherInfo = weatherInfoResponse.map { it.toModel() }
    )
}

fun HourlyWeatherResponse.toModel(): HourlyWeather {
    return HourlyWeather(
        time = formatDate(time, "HH:mm"),
        temperature = temperature,
        weatherInfo = weatherInfoResponse.map { it.toModel() }
    )
}

fun DailyWeatherResponse.toModel(): DailyWeather {
    return DailyWeather(
        time = formatDate(time, "E"),
        temperatureInfo = temperatureInfoResponse.toModel(),
        weatherInfo = weatherInfoResponse.map { it.toModel() }
    )
}

fun WeatherInfoResponse.toModel(): WeatherInfo {
    return WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = "$ICON_URL$icon@2x.png"
    )
}

fun TemperatureInfoResponse.toModel(): TemperatureInfo {
    return TemperatureInfo(min, max)
}

fun formatDate(millis: Long, pattern: String): String {
    val date = Date(millis * 1000)
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(date)
}
