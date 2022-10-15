package com.jxareas.xpensor.ui.main.event

sealed class MainActivityEvent {
    object OpenTheSettingsScreen : MainActivityEvent()
    object OpenTheSelectAccountDialog : MainActivityEvent()
}