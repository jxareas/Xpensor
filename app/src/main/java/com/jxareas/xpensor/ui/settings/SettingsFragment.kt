package com.jxareas.xpensor.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.jxareas.xpensor.R

class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }