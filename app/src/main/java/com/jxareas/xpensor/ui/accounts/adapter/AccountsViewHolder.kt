package com.jxareas.xpensor.ui.accounts.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.databinding.ListItemAccountBinding
import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.OnBindViewHolder
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.utils.extensions.setTint

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