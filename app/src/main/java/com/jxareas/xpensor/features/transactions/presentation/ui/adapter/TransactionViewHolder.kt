package com.jxareas.xpensor.features.transactions.presentation.ui.adapter

import android.content.SharedPreferences
import com.jxareas.xpensor.common.extensions.setIcon
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences.Companion.DEFAULT_CURRENCY
import com.jxareas.xpensor.databinding.CardItemTransactionBinding
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails

class TransactionViewHolder(
    private val binding: CardItemTransactionBinding,
    private val preferences: SharedPreferences,
) : TransactionAdapter.ViewHolder(binding) {

    override fun bind(item: Any) {
        val details = item as TransactionDetails

        with(binding) {
            iconBackground.setTint(details.category.iconColor)
            icon.setIcon(details.category.icon)

            categoryName.text = details.category.name
            cardName.text = details.account.name
            note.text = details.transaction.note
            textAmount.text = details.transaction.amount.toAmountFormat(withMinus = true)
            textCurrency.text =
                preferences.getString(CURRENCY_PREFERENCE_KEY, DEFAULT_CURRENCY)

            note.isSelected = true
            categoryName.isSelected = true
            cardName.isSelected = true
        }
    }
}
