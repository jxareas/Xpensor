package com.jxareas.xpensor.ui.transactions.actions.category.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.domain.model.CategoryWithDetails
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.OnBindViewHolder
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.utils.extensions.setIcon
import com.jxareas.xpensor.utils.extensions.setTint

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