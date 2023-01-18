package com.jxareas.xpensor.common.extensions

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.jxareas.xpensor.R

fun Fragment.postponeEnterTransitionAndStartOnPreDraw() =
    postponeEnterTransition().also {
        requireView().doOnPreDraw { startPostponedEnterTransition() }
    }

fun Fragment.navigateWithNavController(navDirections: NavDirections) =
    findNavController().navigate(navDirections)

fun Fragment.navigateUpWithNavController() =
    findNavController().navigateUp()


fun Fragment.setMenuOnActivity(
    owner: LifecycleOwner = viewLifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    menuProviderCreator: () -> MenuProvider,
) = menuProviderCreator.invoke().let { provider ->
    requireActivity().addMenuProvider(provider, owner, state)
}

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
