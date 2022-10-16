package com.jxareas.xpensor.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.jxareas.xpensor.R
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_COLOR

private val mapOfDrawables = mapOf(
    0 to R.drawable.ic_family,
    1 to R.drawable.ic_finance,
    2 to R.drawable.ic_giftbox,
    3 to R.drawable.ic_grocery,
    4 to R.drawable.ic_health,
    5 to R.drawable.ic_house,
    6 to R.drawable.ic_leisure,
    7 to R.drawable.ic_shopping,
    8 to R.drawable.ic_transport,
    9 to R.drawable.ic_other,
    10 to R.drawable.ic_restaurant,
    11 to R.drawable.ic_services
)

fun ImageView.setIcon(id: Int) {
    this.setImageResource(mapOfDrawables[id] ?: R.drawable.ic_image_error)
}

fun Fragment.getThemeColor(color: Int): Int {
    val value = TypedValue()
    requireContext().theme.resolveAttribute(color, value, true)
    return value.data
}

fun ListItemCategoryBinding.setCategoryAttributes(
    category: CategoryView,
    currency: String?
) {
    this.name.text = category.name
    this.icon.setIcon(category.icon)
    this.iconBackground.setTint(category.iconColor)
    this.amount.text = category.amount.toAmountFormat(withMinus = false)
    this.currency.text = currency

    val color = Color.parseColor(category.iconColor)
    this.amount.setTextColor(color)
    this.currency.setTextColor(color)

    this.item.alpha = if (category.amount == 0.0) 0.3f else 1f
}

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