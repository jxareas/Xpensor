package com.jxareas.xpensor.features.accounts.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.OnBindViewHolder
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.databinding.ListItemAccountBinding
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

class AccountsViewHolder(
    private val binding: ListItemAccountBinding,
    private val userPreferences: UserPreferences,
) : RecyclerView.ViewHolder(binding.root), OnBindViewHolder<AccountUi> {

    override fun bind(item: AccountUi) = with(binding) {
        textViewName.text = item.name
        textViewAmount.text = item.amount.toAmountFormat(withMinus = false)
        textViewCurrency.text = userPreferences.getPreferredCurrencyName()
        iconBackground.setTint(item.color)
    }
}
