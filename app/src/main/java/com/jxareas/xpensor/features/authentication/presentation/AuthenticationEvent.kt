package com.jxareas.xpensor.features.authentication.presentation

sealed class AuthenticationEvent {
    object ForgotPinCode : AuthenticationEvent()
    object OpenMainActivity : AuthenticationEvent()
    object DeletePinCode : AuthenticationEvent()
    object EraseAppData : AuthenticationEvent()
    object SetNewPinCode : AuthenticationEvent()
    object RepeatPinCode : AuthenticationEvent()
}