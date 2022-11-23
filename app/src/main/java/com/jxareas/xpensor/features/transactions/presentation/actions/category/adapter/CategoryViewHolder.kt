package com.jxareas.xpensor.features.transactions.presentation.actions.category.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.OnBindViewHolder
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.common.extensions.setIcon
import com.jxareas.xpensor.common.extensions.setTint

class CategoryViewHolder(
    private val binding: ListItemCategoryBinding,
    private val sharedPreferences: SharedPreferences,
) : RecyclerView.ViewHolder(binding.root), OnBindViewHolder<CategoryWithDetails> {
    override fun bind(item: CategoryWithDetails) = binding.run {
        icon.setIcon(item.category.icon)
        iconBackground.setTint(item.category.iconColor)

        name.text = item.category.name
        amount.text = item.amount.toAmountFormat(withMinus = false)
        currency.text = sharedPreferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)

        name.isSelected = true
    }


}