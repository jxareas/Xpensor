package com.jxareas.xpensor.features.authentication.domain.usecase

import android.content.SharedPreferences
import com.jxareas.xpensor.common.utils.PreferenceUtils
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddUserAuthenticationPin @Inject constructor(private val preferences: SharedPreferences) {

    operator fun invoke(pinCode: String) =
        preferences.edit()
            .putString(PreferenceUtils.AUTH_CODE_PREFERENCE_KEY, pinCode)
            .putBoolean(PreferenceUtils.FIRST_TIME_PREFERENCE_KEY, false)
            .apply()


}