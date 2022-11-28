package com.jxareas.xpensor.features.settings.ui

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.features.authentication.domain.usecase.SetAuthenticationRequiredStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setAuthenticationRequiredStatusUseCase: SetAuthenticationRequiredStatusUseCase,
) : ViewModel() {

    fun setAuthenticationRequired(shouldAuthenticate: Boolean) =
        setAuthenticationRequiredStatusUseCase(shouldAuthenticate)

}