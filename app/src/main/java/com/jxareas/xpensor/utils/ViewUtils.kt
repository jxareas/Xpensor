package com.jxareas.xpensor.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_COLOR

fun AppCompatDelegate.setNightMode(onSelectedTheme: () -> Int) =
    AppCompatDelegate.setDefaultNightMode(
        onSelectedTheme()
    )

fun ImageView.setTint(value: String?) {
    DrawableCompat.setTint(this.drawable, Color.parseColor(value ?: MAIN_COLOR))
}

internal inline infix operator fun <reified T : ViewBinding> ViewGroup.invoke(
    crossinline bindingInflater: LayoutInflater.(parent: ViewGroup, attachToParent: Boolean) -> T,
): T = LayoutInflater.from(context).let { layoutInflater ->
    bindingInflater.invoke(layoutInflater, this, false)
}

fun Fragment.getCurrentDestination() =
    (findNavController().currentDestination as? FragmentNavigator.Destination)?.className
        ?: (findNavController().currentDestination as? DialogFragmentNavigator.Destination)?.className

fun toast(context: Context?, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}