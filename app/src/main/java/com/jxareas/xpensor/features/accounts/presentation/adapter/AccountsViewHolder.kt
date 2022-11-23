package com.jxareas.xpensor.features.accounts.presentation.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.databinding.ListItemAccountBinding
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.OnBindViewHolder
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.common.extensions.setTint

class AccountsViewHolder(
    private val preferences: SharedPreferences,
    private val binding: ListItemAccountBinding,
) : RecyclerView.ViewHolder(binding.root), OnBindViewHolder<AccountWithDetails> {

    override fun bind(item: AccountWithDetails) = with(binding) {
        textViewName.text = item.name
        textViewAmount.text = item.amount.toAmountFormat(withMinus = false)
        textViewCurrency.text = preferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)
        iconBackground.setTint(item.color)
    }

}