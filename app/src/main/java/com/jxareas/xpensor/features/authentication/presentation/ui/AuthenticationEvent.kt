package com.jxareas.xpensor.features.authentication.presentation.ui

sealed class AuthenticationEvent {
    object ForgotPinCode : AuthenticationEvent()
    object OpenMainActivity : AuthenticationEvent()
    object DeletePinCode : AuthenticationEvent()
    object EraseAppData : AuthenticationEvent()
    object SetNewPinCode : AuthenticationEvent()
    object RepeatPinCode : AuthenticationEvent()
}
