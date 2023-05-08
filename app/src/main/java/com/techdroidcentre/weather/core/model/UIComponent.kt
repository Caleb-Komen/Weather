package com.techdroidcentre.weather.core.model

sealed interface UIComponent {
    data class Dialog(val title: String, val description: String): UIComponent

    data class Banner(val description: String): UIComponent
}