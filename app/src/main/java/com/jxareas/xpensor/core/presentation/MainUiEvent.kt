package com.jxareas.xpensor.core.presentation

sealed class MainUiEvent {
    object OpenTheSettingsScreen : MainUiEvent()
    object OpenTheSelectAccountDialog : MainUiEvent()
}
