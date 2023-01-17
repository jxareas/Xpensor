package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.common.extensions.setIcon
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.OnBindViewHolder
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

class CategoryViewHolder(
    private val binding: ListItemCategoryBinding,
    private val userPreferences: UserPreferences,
) : RecyclerView.ViewHolder(binding.root), OnBindViewHolder<CategoryWithAmountUi> {
    override fun bind(item: CategoryWithAmountUi) = binding.run {
        icon.setIcon(item.category.icon)
        iconBackground.setTint(item.category.iconColor)

        name.text = item.category.name
        amount.text = item.amount.toAmountFormat(withMinus = false)
        currency.text = userPreferences.getPreferredCurrencyName()
        name.isSelected = true
    }
}
