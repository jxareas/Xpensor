package com.jxareas.xpensor.features.authentication.presentation.ui

sealed class AuthenticationUiEvent {
    object ForgotPinCode : AuthenticationUiEvent()
    object OpenMainActivity : AuthenticationUiEvent()
    object DeletePinCode : AuthenticationUiEvent()
    object EraseAppData : AuthenticationUiEvent()
    object SetNewPinCode : AuthenticationUiEvent()
    object RepeatPinCode : AuthenticationUiEvent()
}
