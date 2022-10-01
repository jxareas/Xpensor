package com.jxareas.xpensor.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

fun Fragment.getCurrentDestination() =
    (findNavController().currentDestination as? FragmentNavigator.Destination)?.className
        ?: (findNavController().currentDestination as? DialogFragmentNavigator.Destination)?.className

fun toast(context: Context?, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}