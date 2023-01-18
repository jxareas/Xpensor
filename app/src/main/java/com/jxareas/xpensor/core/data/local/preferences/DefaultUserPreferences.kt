package com.jxareas.xpensor.core.data.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.jxareas.xpensor.common.extensions.getStringOrDefault
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences.Companion.DEFAULT_COLOR
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences.Companion.DEFAULT_CURRENCY
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences.Companion.DEFAULT_THEME
import javax.inject.Inject

class DefaultUserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : UserPreferences {
    companion object {
        const val KEY_CURRENCY = "currency"
        const val KEY_THEME = "theme"
        const val THEME_DARK = "dark"
        const val THEME_LIGHT = "light"
    }

    override fun getMainColor(): String =
        DEFAULT_COLOR

    override fun getPreferredTheme(): String =
        sharedPreferences.getStringOrDefault(KEY_THEME, DEFAULT_THEME)

    override fun getPreferredCurrencyName(): String =
        sharedPreferences.getStringOrDefault(KEY_CURRENCY, DEFAULT_CURRENCY)

    override fun savePreferredTheme(theme: String) =
        sharedPreferences.edit {
            putString(KEY_THEME, theme)
        }

    override fun savePreferredCurrencyName(currencyName: String) =
        sharedPreferences.edit {
            putString(KEY_CURRENCY, currencyName)
        }
}
