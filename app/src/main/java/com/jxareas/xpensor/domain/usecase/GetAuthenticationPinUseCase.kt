package com.jxareas.xpensor.domain.usecase

import android.content.SharedPreferences
import com.jxareas.xpensor.domain.model.PinCode
import com.jxareas.xpensor.utils.PreferenceUtils
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetAuthenticationPinUseCase @Inject constructor(private val preferences: SharedPreferences) {

    operator fun invoke(): PinCode {
        val code =
            preferences.getString(PreferenceUtils.AUTH_CODE_PREFERENCE_KEY, PinCode.EMPTY_CODE)
                ?: PinCode.EMPTY_CODE
        return PinCode(code)
    }

}