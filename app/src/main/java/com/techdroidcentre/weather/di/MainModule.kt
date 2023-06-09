package com.techdroidcentre.weather.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.techdroidcentre.weather.core.SettingsRepository
import com.techdroidcentre.weather.core.WeatherRepository
import com.techdroidcentre.weather.data.DefaultSettingsRepository
import com.techdroidcentre.weather.data.DefaultWeatherRepository
import com.techdroidcentre.weather.data.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainModule {
    @Singleton
    @Provides
    fun provideWeatherApiService(): WeatherApiService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .client(client)
            .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherApiService: WeatherApiService
    ): WeatherRepository {
        return DefaultWeatherRepository(weatherApiService)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository {
        return DefaultSettingsRepository(context)
    }
}