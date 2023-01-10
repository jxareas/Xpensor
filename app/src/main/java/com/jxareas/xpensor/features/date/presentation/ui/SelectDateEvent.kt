package com.jxareas.xpensor.features.date.presentation.ui

sealed class SelectDateEvent {
    object Today : SelectDateEvent()
    object Week : SelectDateEvent()
    object Month : SelectDateEvent()
    object Year : SelectDateEvent()
    object AllTime : SelectDateEvent()
    object CustomDate : SelectDateEvent()
}
