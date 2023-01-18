package com.jxareas.xpensor.features.date.presentation.ui

sealed class SelectDateUiEvent {
    object Today : SelectDateUiEvent()
    object Week : SelectDateUiEvent()
    object Month : SelectDateUiEvent()
    object Year : SelectDateUiEvent()
    object AllTime : SelectDateUiEvent()
    object CustomDate : SelectDateUiEvent()
}
