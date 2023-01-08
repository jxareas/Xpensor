package com.jxareas.xpensor.common.extensions

import android.content.SharedPreferences

fun SharedPreferences.getStringOrDefault(key: String, default: String): String =
    getString(key, default) ?: default
