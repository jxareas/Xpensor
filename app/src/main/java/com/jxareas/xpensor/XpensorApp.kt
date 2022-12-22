package com.jxareas.xpensor

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.jxareas.xpensor.common.utils.PreferenceUtils.THEME_DARK
import com.jxareas.xpensor.common.utils.PreferenceUtils.THEME_DEFAULT
import com.jxareas.xpensor.common.utils.PreferenceUtils.THEME_LIGHT
import com.jxareas.xpensor.common.utils.PreferenceUtils.THEME_PREFERENCE_KEY
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class XpensorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val selectedTheme = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(THEME_PREFERENCE_KEY, THEME_DEFAULT)

        AppCompatDelegate.setDefaultNightMode(
            when (selectedTheme) {
                THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}
