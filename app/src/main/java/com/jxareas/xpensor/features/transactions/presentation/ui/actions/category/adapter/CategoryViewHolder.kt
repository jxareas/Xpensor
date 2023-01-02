package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.common.extensions.setIcon
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.OnBindViewHolder
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

class CategoryViewHolder(
    private val binding: ListItemCategoryBinding,
    private val sharedPreferences: SharedPreferences,
) : RecyclerView.ViewHolder(binding.root), OnBindViewHolder<CategoryWithAmountUi> {
    override fun bind(item: CategoryWithAmountUi) = binding.run {
        icon.setIcon(item.categoryUi.icon)
        iconBackground.setTint(item.categoryUi.iconColor)

        name.text = item.categoryUi.name
        amount.text = item.amount.toAmountFormat(withMinus = false)
        currency.text = sharedPreferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)

        name.isSelected = true
    }
}
