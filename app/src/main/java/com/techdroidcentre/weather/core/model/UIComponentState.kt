package com.techdroidcentre.weather.core.model

sealed class UIComponentState {
    object Show: UIComponentState()

    object Hide: UIComponentState()
}