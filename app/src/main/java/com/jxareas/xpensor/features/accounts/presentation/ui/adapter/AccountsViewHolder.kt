package com.jxareas.xpensor.features.accounts.presentation.ui.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.OnBindViewHolder
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.databinding.ListItemAccountBinding
import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount

class AccountsViewHolder(
    private val preferences: SharedPreferences,
    private val binding: ListItemAccountBinding,
) : RecyclerView.ViewHolder(binding.root), OnBindViewHolder<UiAccount> {

    override fun bind(item: UiAccount) = with(binding) {
        textViewName.text = item.name
        textViewAmount.text = item.amount.toAmountFormat(withMinus = false)
        textViewCurrency.text = preferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)
        iconBackground.setTint(item.color)
    }

}