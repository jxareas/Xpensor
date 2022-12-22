package com.jxareas.xpensor.features.date.presentation.ui

sealed class DateSelectedEvent {
    object Today : DateSelectedEvent()
    object Week : DateSelectedEvent()
    object Month : DateSelectedEvent()
    object Year : DateSelectedEvent()
    object AllTime : DateSelectedEvent()
    object CustomDate : DateSelectedEvent()
}
