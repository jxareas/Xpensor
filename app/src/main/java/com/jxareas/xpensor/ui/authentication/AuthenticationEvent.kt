package com.jxareas.xpensor.ui.authentication

sealed class AuthenticationEvent {
    object ForgotPinCode : AuthenticationEvent()
    object OpenMainActivity : AuthenticationEvent()
    object DeletePinCode : AuthenticationEvent()
    object EraseAppData : AuthenticationEvent()
    object SetNewPinCode : AuthenticationEvent()
    object RepeatPinCode : AuthenticationEvent()
}