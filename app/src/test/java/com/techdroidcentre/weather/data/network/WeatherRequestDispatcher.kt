package com.techdroidcentre.weather.data.network

import com.techdroidcentre.weather.BuildConfig
import com.techdroidcentre.weather.data.network.Util.readFromFile
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

class WeatherRequestDispatcher: Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            WEATHER_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(readFromFile("weather.json"))
            }
            else -> throw IllegalArgumentException("Unknown request path '${request.path}'")
        }
    }

    companion object {
        const val WEATHER_URL = "/data/3.0/onecall?lat=-1.294&lon=36.87&units=standard&exclude=minutely&appid=${BuildConfig.OPEN_WEATHER_API_KEY}"
    }
}