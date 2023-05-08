package com.techdroidcentre.weather.core

import com.techdroidcentre.weather.core.model.UIComponent

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val uiComponent: UIComponent): Result<Nothing>()
}
