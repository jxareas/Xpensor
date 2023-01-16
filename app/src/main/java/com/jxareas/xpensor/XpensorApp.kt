package com.jxareas.xpensor

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.jxareas.xpensor.core.data.local.preferences.DefaultUserPreferences.Companion.THEME_DARK
import com.jxareas.xpensor.core.data.local.preferences.DefaultUserPreferences.Companion.THEME_LIGHT
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class XpensorApp : Application() {

    @Inject
    internal lateinit var userPreferences: UserPreferences

    override fun onCreate() {
        super.onCreate()

        val isNightMode = when (userPreferences.getPreferredTheme()) {
            THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(isNightMode)
    }
}
