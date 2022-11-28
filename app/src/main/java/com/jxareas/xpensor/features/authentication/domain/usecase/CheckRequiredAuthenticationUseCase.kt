package com.jxareas.xpensor.features.authentication.domain.usecase

import android.content.SharedPreferences
import com.jxareas.xpensor.common.utils.PreferenceUtils.IS_AUTHENTICATION_REQUIRED_KEY
import javax.inject.Inject

class CheckRequiredAuthenticationUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    operator fun invoke(): Boolean =
        sharedPreferences
            .getBoolean(IS_AUTHENTICATION_REQUIRED_KEY, true)
}