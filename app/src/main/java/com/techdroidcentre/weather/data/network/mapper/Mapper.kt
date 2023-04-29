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
        weatherInfo = weatherInfoResponse.toModel()
    )
}

fun HourlyWeatherResponse.toModel(): HourlyWeather {
    return HourlyWeather(
        time = time,
        temperature = temperature,
        weatherInfo = weatherInfoResponse.toModel()
    )
}

fun DailyWeatherResponse.toModel(): DailyWeather {
    return DailyWeather(
        temperatureInfo = temperatureInfoResponse.toModel(),
        weatherInfo = weatherInfoResponse.toModel()
    )
}

fun WeatherInfoResponse.toModel(): WeatherInfo {
    return WeatherInfo(id, main, description, icon)
}

fun TemperatureInfoResponse.toModel(): TemperatureInfo {
    return TemperatureInfo(min, max)
}