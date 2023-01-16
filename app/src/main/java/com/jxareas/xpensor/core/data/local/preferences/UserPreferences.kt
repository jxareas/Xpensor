package com.jxareas.xpensor.core.data.local.preferences

interface UserPreferences {

    companion object {
        const val DEFAULT_COLOR = "#1b2838"
        const val DEFAULT_CURRENCY = "USD"
        const val DEFAULT_THEME = "default"
    }

    fun getMainColor(): String
    fun getPreferredTheme(): String
    fun getPreferredCurrencyName(): String
    fun savePreferredTheme(theme: String)
    fun savePreferredCurrencyName(currencyName: String)
}
