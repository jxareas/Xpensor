package com.jxareas.xpensor.common.extensions

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.jxareas.xpensor.R

fun Fragment.getDivider(context: Context) = DividerItemDecoration(
    context,
    DividerItemDecoration.VERTICAL,
).apply {
    setDrawable(
        requireNotNull(
            ContextCompat.getDrawable(
                context,
                R.drawable.divider_layer,
            ),
        ),
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

// fun Fragment.getCurrentDestination() =
//    (findNavController().currentDestination as? FragmentNavigator.Destination)?.className
//        ?:
//        (findNavController().currentDestination as? DialogFragmentNavigator.Destination)?.className

fun Fragment.getThemeColor(color: Int): Int {
    val value = TypedValue()
    requireContext().theme.resolveAttribute(color, value, true)
    return value.data
}

fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    showToast(requireContext(), text, duration)
}

fun Fragment.showToast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    val text = resources.getString(stringRes)
    showToast(requireContext(), text, duration)
}

private fun showToast(context: Context?, text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}
