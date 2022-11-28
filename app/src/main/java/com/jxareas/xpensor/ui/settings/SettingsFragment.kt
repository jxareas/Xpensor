package com.jxareas.xpensor.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.jxareas.xpensor.R
<<<<<<< Updated upstream:app/src/main/java/com/jxareas/xpensor/ui/settings/SettingsFragment.kt
import com.jxareas.xpensor.utils.PreferenceUtils.THEME_DARK
import com.jxareas.xpensor.utils.PreferenceUtils.THEME_LIGHT
import com.jxareas.xpensor.utils.PreferenceUtils.THEME_PREFERENCE_KEY
=======
import com.jxareas.xpensor.common.utils.PreferenceUtils.REQUIRE_PASSWORD_ALWAYS
import com.jxareas.xpensor.common.utils.PreferenceUtils.REQUIRE_PASSWORD_NEVER
import com.jxareas.xpensor.common.utils.PreferenceUtils.REQUIRE_PASSWORD_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.THEME_DARK
import com.jxareas.xpensor.common.utils.PreferenceUtils.THEME_LIGHT
import com.jxareas.xpensor.common.utils.PreferenceUtils.THEME_PREFERENCE_KEY
import dagger.hilt.android.AndroidEntryPoint
>>>>>>> Stashed changes:app/src/main/java/com/jxareas/xpensor/features/settings/ui/SettingsFragment.kt

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<ListPreference>(THEME_PREFERENCE_KEY)?.setOnPreferenceChangeListener { _, newValue ->
            AppCompatDelegate.setDefaultNightMode(
                when (newValue) {
                    THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
            true
        }

        findPreference<ListPreference>(REQUIRE_PASSWORD_PREFERENCE_KEY)?.setOnPreferenceChangeListener { _, newValue ->
            val shouldAuthenticate = when (newValue) {
                REQUIRE_PASSWORD_ALWAYS -> true
                REQUIRE_PASSWORD_NEVER -> false
                else -> false
            }
            viewModel.setAuthenticationRequired(shouldAuthenticate)
            true
        }

    }
}