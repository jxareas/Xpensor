package com.jxareas.xpensor.common.extensions

import android.content.SharedPreferences

fun SharedPreferences.getStringOrDefault(stringKey: String, stringDefault: String): String =
    getString(stringKey, stringDefault) ?: stringDefault
