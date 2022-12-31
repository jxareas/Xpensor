package com.jxareas.xpensor.features.date.presentation.ui

sealed class DateSelectedUiEvent {
    object Today : DateSelectedUiEvent()
    object Week : DateSelectedUiEvent()
    object Month : DateSelectedUiEvent()
    object Year : DateSelectedUiEvent()
    object AllTime : DateSelectedUiEvent()
    object CustomDate : DateSelectedUiEvent()
}
