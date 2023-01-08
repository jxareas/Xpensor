package com.jxareas.xpensor.features.authentication.domain.usecase

import com.jxareas.xpensor.features.authentication.domain.manager.AuthenticationManager
import javax.inject.Inject

class ValidateFirstTimeLaunchUseCase @Inject constructor(
    private val authenticationManager: AuthenticationManager,
) {

    operator fun invoke() =
        authenticationManager.isFirstAppLaunch

}
