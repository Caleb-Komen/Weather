package com.techdroidcentre.weather.data.network

import com.google.common.truth.Truth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.techdroidcentre.weather.core.Result
import com.techdroidcentre.weather.core.WeatherRepository
import com.techdroidcentre.weather.data.DefaultWeatherRepository
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class WeatherRepositoryTest {
    private lateinit var mockWebServer: MockWebServer

    private lateinit var weatherApiService: WeatherApiService

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = WeatherRequestDispatcher()
        mockWebServer.start()

        weatherApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(getOkHttpClient())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WeatherApiService::class.java)

        weatherRepository = DefaultWeatherRepository(weatherApiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Test
    fun getWeatherData() = runBlocking{
        val data = weatherRepository.getWeatherData(-1.294f, 36.87f, "standard")
        data.collect { result ->
            when (result) {
                is Result.Success -> {
                    Truth.assertThat(result.data.currentWeather.temperature).isEqualTo(293.36)
                }
                is Result.Error -> {
                    Truth.assertThat(result.message).isNotEmpty()
                }
            }

        }
    }
}