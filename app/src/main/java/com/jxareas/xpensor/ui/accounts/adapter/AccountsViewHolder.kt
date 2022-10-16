package com.jxareas.xpensor.ui.accounts.adapter

import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.xpensor.databinding.ListItemAccountBinding
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.utils.Binder
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.utils.setTint

class AccountsViewHolder(
    private val preferences: SharedPreferences,
    private val binding: ListItemAccountBinding,
) :
    RecyclerView.ViewHolder(binding.root), Binder<Account> {

    override fun bind(listItem: Account) = with(binding) {
        textViewName.text = listItem.name
        textViewAmount.text = listItem.amount.toAmountFormat(withMinus = false)
        textViewCurrency.text = preferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)
        iconBackground.setTint(listItem.color)
    }

}