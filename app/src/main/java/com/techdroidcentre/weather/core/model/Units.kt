package com.techdroidcentre.weather.core.model

enum class Units(val value: String, val tempSymbol: String, val windSpeed: String) {
    STANDARD("standard","K", "m/s"),
    METRIC("metric","°C", "m/s"),
    IMPERIAL("imperial","°F", "mph"),
}