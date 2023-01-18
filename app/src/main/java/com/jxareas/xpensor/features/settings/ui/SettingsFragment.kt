package com.jxareas.xpensor.features.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.jxareas.xpensor.R
import com.jxareas.xpensor.core.data.local.preferences.DefaultUserPreferences.Companion.KEY_THEME
import com.jxareas.xpensor.core.data.local.preferences.DefaultUserPreferences.Companion.THEME_DARK
import com.jxareas.xpensor.core.data.local.preferences.DefaultUserPreferences.Companion.THEME_LIGHT

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<ListPreference>(KEY_THEME)?.setOnPreferenceChangeListener { _, newValue ->
            AppCompatDelegate.setDefaultNightMode(
                when (newValue) {
                    THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                },
            )
            true
        }
    }
}
