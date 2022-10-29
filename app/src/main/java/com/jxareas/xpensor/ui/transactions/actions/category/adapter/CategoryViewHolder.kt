package com.jxareas.xpensor.ui.transactions.actions.category.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.utils.Binder
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.utils.setIcon
import com.jxareas.xpensor.utils.setTint

class CategoryViewHolder(
    private val binding: ListItemCategoryBinding,
    private val sharedPreferences: SharedPreferences,
) : RecyclerView.ViewHolder(binding.root), Binder<CategoryView> {
    override fun bind(item: CategoryView) = binding.run {
        icon.setIcon(item.icon)
        iconBackground.setTint(item.iconColor)

        name.text = item.name
        amount.text = item.amount.toAmountFormat(withMinus = false)
        currency.text = sharedPreferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)

        name.isSelected = true
    }


}