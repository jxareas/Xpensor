package com.jxareas.xpensor.ui.date.event

sealed class DateSelectedEvent {
    object Today : DateSelectedEvent()
    object Week : DateSelectedEvent()
    object Month : DateSelectedEvent()
    object Year : DateSelectedEvent()
    object AllTime : DateSelectedEvent()
    object CustomDate : DateSelectedEvent()
}