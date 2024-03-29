package com.jxareas.xpensor.features.authentication.presentation.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.PreferenceUtils.FIRST_TIME_PREFERENCE_KEY
import com.jxareas.xpensor.features.authentication.domain.model.PinCode
import com.jxareas.xpensor.features.authentication.domain.usecase.AddUserAuthenticationPin
import com.jxareas.xpensor.features.authentication.domain.usecase.GetAuthenticationPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val addUserAuthenticationPin: AddUserAuthenticationPin,
    private val getAuthenticationPinUseCase: GetAuthenticationPinUseCase,
    private val preferences: SharedPreferences,
) : ViewModel() {

    private val _pinCode = MutableStateFlow(PinCode.EMPTY_CODE)
    val pinCode = _pinCode.asStateFlow()

    private val _events = MutableSharedFlow<AuthenticationEvent>()
    val events = _events.asSharedFlow()

    private val _temporaryPinCode = MutableStateFlow(PinCode.EMPTY_CODE)

    fun onButtonNumberClick(value: String) {
        _pinCode.value += value
        if (_pinCode.value.length == PinCode.MAX_CODE_LENGTH) {
            checkTheCode()
        }
    }

    private fun checkTheCode() {
        viewModelScope.launch {
            if (!isAppLaunchedFirstTime()) {
                val currentUserPinCode = getAuthenticationPinUseCase.invoke().code

                val authenticationEvent = if (_pinCode.value == currentUserPinCode)
                    AuthenticationEvent.OpenMainActivity
                else AuthenticationEvent.DeletePinCode
                _events.emit(authenticationEvent)
            } else checkUserAuthentication()
        }
    }

    private suspend fun checkUserAuthentication() {
        if (_temporaryPinCode.value != PinCode.EMPTY_CODE) {
            if (_temporaryPinCode.value == _pinCode.value) {
                addUserAuthenticationPin.invoke(_temporaryPinCode.value)
                _events.emit(AuthenticationEvent.OpenMainActivity)
            } else {
                _temporaryPinCode.value = PinCode.EMPTY_CODE
                _events.emit(AuthenticationEvent.DeletePinCode)
                _events.emit(AuthenticationEvent.SetNewPinCode)
            }
        } else {
            _temporaryPinCode.value = _pinCode.value
            clearCode()
            _events.emit(AuthenticationEvent.RepeatPinCode)
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
        _events.emit(AuthenticationEvent.ForgotPinCode)
    }

    fun onEraseDataClick() = launchScoped {
        _events.emit(AuthenticationEvent.EraseAppData)
    }

    fun isAppLaunchedFirstTime(): Boolean =
        preferences.getBoolean(FIRST_TIME_PREFERENCE_KEY, true)
}
