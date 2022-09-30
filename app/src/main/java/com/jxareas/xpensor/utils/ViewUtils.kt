package com.jxareas.xpensor.utils

import android.content.Context
import android.widget.Toast

fun toast(context: Context?, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}