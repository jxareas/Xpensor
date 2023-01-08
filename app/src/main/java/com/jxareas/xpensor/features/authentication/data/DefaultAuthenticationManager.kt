package com.jxareas.xpensor.features.authentication.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.jxareas.xpensor.features.authentication.domain.manager.AuthenticationManager
import com.jxareas.xpensor.features.authentication.domain.model.PinCode
import javax.inject.Inject

class DefaultAuthenticationManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : AuthenticationManager {
    companion object {
        private const val KEY_FIRST_TIME_LAUNCHED = "first_time"
        private const val KEY_AUTH_CODE = "auth_code"
    }

    override val isFirstAppLaunch: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_TIME_LAUNCHED, false)

    override fun getUserAuthenticationPin(): PinCode {
        val userPin =
            sharedPreferences.getString(KEY_AUTH_CODE, PinCode.EMPTY_CODE) ?: PinCode.EMPTY_CODE
        return PinCode(userPin)
    }

    override fun setUserAuthenticationPin(pinCode: PinCode) =
        sharedPreferences.edit {
            putString(KEY_AUTH_CODE, pinCode.code)
            putBoolean(KEY_FIRST_TIME_LAUNCHED, true)
        }

}


