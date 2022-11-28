package com.jxareas.xpensor.features.authentication.domain.usecase

import android.content.SharedPreferences
import com.jxareas.xpensor.common.utils.PreferenceUtils.IS_AUTHENTICATION_REQUIRED_KEY
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SetAuthenticationRequiredStatusUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    operator fun invoke(shouldAuthenticate: Boolean) =
        sharedPreferences
            .edit()
            .putBoolean(IS_AUTHENTICATION_REQUIRED_KEY, shouldAuthenticate)
            .apply()

}