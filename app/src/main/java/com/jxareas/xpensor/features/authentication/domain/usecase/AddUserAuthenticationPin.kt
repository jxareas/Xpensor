package com.jxareas.xpensor.features.authentication.domain.usecase

import com.jxareas.xpensor.features.authentication.domain.manager.AuthenticationManager
import com.jxareas.xpensor.features.authentication.domain.model.PinCode
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddUserAuthenticationPin @Inject constructor(
    private val authenticationManager: AuthenticationManager,
) {

    operator fun invoke(pinCode: PinCode) =
        authenticationManager.setUserAuthenticationPin(pinCode)
}
