package com.jxareas.xpensor.features.authentication.presentation.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.PreferenceUtils.FIRST_TIME_PREFERENCE_KEY
import com.jxareas.xpensor.features.authentication.domain.model.PinCode
import com.jxareas.xpensor.features.authentication.domain.usecase.AddUserAuthenticationPin
import com.jxareas.xpensor.features.authentication.domain.usecase.CheckRequiredAuthenticationUseCase
import com.jxareas.xpensor.features.authentication.domain.usecase.GetAuthenticationPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val addUserAuthenticationPin: AddUserAuthenticationPin,
    private val getAuthenticationPinUseCase: GetAuthenticationPinUseCase,
    private val checkRequiredAuthenticationUseCase: CheckRequiredAuthenticationUseCase,
    private val preferences: SharedPreferences,
) : ViewModel() {

    private val _pinCode = MutableStateFlow(PinCode.EMPTY_CODE)
    val pinCode = _pinCode.asStateFlow()

    private val _events = Channel<AuthenticationEvent>(Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    private val _temporaryPinCode = MutableStateFlow(PinCode.EMPTY_CODE)


    fun checkForRequiredAuthentication() = launchScoped {
        val shouldAuthenticate = checkRequiredAuthenticationUseCase()
        if (!shouldAuthenticate)
            _events.send(AuthenticationEvent.OpenMainActivity)
    }

    fun onButtonNumberClick(value: String) {
        _pinCode.value += value
        if (_pinCode.value.length == PinCode.MAX_CODE_LENGTH) {
            checkTheCode()
        }
    }

    private fun checkTheCode() {
        viewModelScope.launch {
            if (!isAppLaunchedFirstTime()) {
                val currentUserPinCode = getAuthenticationPinUseCase().code

                val authenticationEvent = if (_pinCode.value == currentUserPinCode)
                    AuthenticationEvent.OpenMainActivity
                else AuthenticationEvent.DeletePinCode
                _events.send(authenticationEvent)
            } else checkUserAuthentication()
        }
    }

    private suspend fun checkUserAuthentication() {
        if (_temporaryPinCode.value != PinCode.EMPTY_CODE) {
            if (_temporaryPinCode.value == _pinCode.value) {
                addUserAuthenticationPin(_temporaryPinCode.value)
                _events.send(AuthenticationEvent.OpenMainActivity)
            } else {
                _temporaryPinCode.value = PinCode.EMPTY_CODE
                _events.send(AuthenticationEvent.DeletePinCode)
                _events.send(AuthenticationEvent.SetNewPinCode)
            }
        } else {
            _temporaryPinCode.value = _pinCode.value
            clearCode()
            _events.send(AuthenticationEvent.RepeatPinCode)
        }
    }

    fun clearCode() {
        _pinCode.value = PinCode.EMPTY_CODE
    }

    fun backspaceButtonClick() {
        if (_pinCode.value.isNotEmpty())
            _pinCode.value = _pinCode.value.dropLast(1)
    }

    fun forgotButtonClick() = launchScoped {
        _events.send(AuthenticationEvent.ForgotPinCode)
    }

    fun onEraseDataClick() = launchScoped {
        _events.send(AuthenticationEvent.EraseAppData)
    }

    fun isAppLaunchedFirstTime(): Boolean =
        preferences.getBoolean(FIRST_TIME_PREFERENCE_KEY, true)


}