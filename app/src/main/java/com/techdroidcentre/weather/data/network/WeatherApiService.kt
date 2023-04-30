package com.techdroidcentre.weather.data.network

import com.techdroidcentre.weather.BuildConfig
import com.techdroidcentre.weather.data.network.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat")latitude: Float,
        @Query("lon")longitude: Float,
        @Query("units")units: String,
        @Query("exclude")exclude: String = "minutely",
        @Query("appid")appId: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): WeatherResponse
}