package com.jxareas.xpensor.features.authentication.domain.manager

import com.jxareas.xpensor.features.authentication.domain.model.PinCode

interface AuthenticationManager {
    val isFirstAppLaunch: Boolean
    fun getUserAuthenticationPin(): PinCode
    fun setUserAuthenticationPin(pinCode: PinCode)
}
