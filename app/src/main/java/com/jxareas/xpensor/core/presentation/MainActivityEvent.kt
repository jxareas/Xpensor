package com.jxareas.xpensor.core.presentation

sealed class MainActivityEvent {
    object OpenTheSettingsScreen : MainActivityEvent()
    object OpenTheSelectAccountDialog : MainActivityEvent()
}
