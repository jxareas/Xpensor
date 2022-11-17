package com.jxareas.xpensor.utils.extensions

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.viewbinding.ViewBinding
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.domain.model.CategoryWithDetails
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat

internal inline infix operator fun <reified VB : ViewBinding> ViewGroup.invoke(
    crossinline bindingInflater: LayoutInflater.(parent: ViewGroup, attachToParent: Boolean) -> VB,
): VB = LayoutInflater.from(context).let inflaterScoped@{ layoutInflater ->
    bindingInflater.invoke(layoutInflater, this, false)
}


fun Resources.getLong(@IntegerRes integerRes: Int): Long =
    getInteger(integerRes).toLong()


fun ListItemCategoryBinding.setCategoryAttributes(
    details: CategoryWithDetails,
    currency: String?,
) {
    this.name.text = details.category.name
    this.icon.setIcon(details.category.icon)
    this.iconBackground.setTint(details.category.iconColor)
    this.amount.text = details.amount.toAmountFormat(withMinus = false)
    this.currency.text = currency

    val color = Color.parseColor(details.category.iconColor)
    this.amount.setTextColor(color)
    this.currency.setTextColor(color)

    this.item.alpha = if (details.amount == 0.0) 0.3f else 1f
}
