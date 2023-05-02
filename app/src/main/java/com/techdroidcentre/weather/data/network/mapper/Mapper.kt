package com.techdroidcentre.weather.data.network.mapper

import com.techdroidcentre.weather.core.model.CurrentWeather
import com.techdroidcentre.weather.core.model.DailyWeather
import com.techdroidcentre.weather.core.model.HourlyWeather
import com.techdroidcentre.weather.core.model.TemperatureInfo
import com.techdroidcentre.weather.core.model.Units
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
import kotlin.math.roundToInt

const val ICON_URL = "https://openweathermap.org/img/wn/"

fun WeatherResponse.toModel(units: Units): Weather {
    return Weather(
        currentWeather = currentWeatherResponse.toModel(units),
        hourlyWeather = hourlyWeatherResponse.map { it.toModel(units) },
        dailyWeather = dailyWeatherResponse.map { it.toModel(units) }
    )
}

fun CurrentWeatherResponse.toModel(units: Units): CurrentWeather {
    return CurrentWeather(
        temperature = formatTemperature(temperature, units),
        feelsLike = formatTemperature(feelsLike, units),
        humidity = "$humidity%",
        windSpeed = formatWindSpeed(windSpeed, units),
        weatherInfo = weatherInfoResponse.map { it.toModel() }
    )
}

fun HourlyWeatherResponse.toModel(units: Units): HourlyWeather {
    return HourlyWeather(
        time = formatDate(time, "HH:mm"),
        temperature = formatTemperature(temperature, units),
        weatherInfo = weatherInfoResponse.map { it.toModel() }
    )
}

fun DailyWeatherResponse.toModel(units: Units): DailyWeather {
    return DailyWeather(
        time = formatDate(time, "E"),
        temperatureInfo = temperatureInfoResponse.toModel(units),
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

fun TemperatureInfoResponse.toModel(units: Units): TemperatureInfo {
    return TemperatureInfo(min = formatTemperature(min, units), max = formatTemperature(max, units))
}

fun formatDate(millis: Long, pattern: String): String {
    val date = Date(millis * 1000)
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(date)
}

fun formatTemperature(temp: Float, units: Units): String {
    return when (units) {
        Units.STANDARD -> {
            "${temp.roundToInt()}${units.tempSymbol}"
        }
        Units.METRIC -> {
            "${temp.roundToInt()}${units.tempSymbol}"
        }
        Units.IMPERIAL -> {
            "${temp.roundToInt()}${units.tempSymbol}"
        }
    }
}

fun formatWindSpeed(windSpeed: Float, units: Units): String {
    return when (units) {
        Units.STANDARD -> {
            "${windSpeed.roundToInt()}${units.windSpeed}"
        }
        Units.METRIC -> {
            "${windSpeed.roundToInt()}${units.windSpeed}"
        }
        Units.IMPERIAL -> {
            "${windSpeed.roundToInt()}${units.windSpeed}"
        }
    }
}
