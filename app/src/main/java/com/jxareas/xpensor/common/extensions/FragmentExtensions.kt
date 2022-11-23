package com.jxareas.xpensor.common.extensions

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.jxareas.xpensor.R

fun Fragment.getDivider(context: Context) = DividerItemDecoration(
    context,
    DividerItemDecoration.VERTICAL
).apply {
    setDrawable(
        requireNotNull(
            ContextCompat.getDrawable(
                context,
                R.drawable.divider_layer
            )
        )
    )
}

fun Fragment.showSnackbar(
    errorMessage: String,
    dismissMessage: String = getString(R.string.dismiss),
    length: Int = Snackbar.LENGTH_LONG,
) = view?.let { rootView ->
    Snackbar.make(rootView, errorMessage, length)
        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
        .setAction(dismissMessage) {
        }
        .show()
}

fun Fragment.getCurrentDestination() =
    (findNavController().currentDestination as? FragmentNavigator.Destination)?.className
        ?: (findNavController().currentDestination as? DialogFragmentNavigator.Destination)?.className

fun Fragment.getThemeColor(color: Int): Int {
    val value = TypedValue()
    requireContext().theme.resolveAttribute(color, value, true)
    return value.data
}

fun toast(context: Context?, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}