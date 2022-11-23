package com.jxareas.xpensor.features.transactions.presentation.adapter

import android.content.SharedPreferences
import com.jxareas.xpensor.databinding.CardItemTransactionBinding
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.common.extensions.setIcon
import com.jxareas.xpensor.common.extensions.setTint

class TransactionViewHolder(
    private val binding: CardItemTransactionBinding,
    private val preferences: SharedPreferences,
) : TransactionAdapter.ViewHolder(binding) {

    override fun bind(item: Any) {
        val transactionView = item as TransactionWithDetails

        with(binding) {
            iconBackground.setTint(transactionView.category.iconColor)
            icon.setIcon(transactionView.category.icon)

            categoryName.text = transactionView.category.name
            cardName.text = transactionView.account.name
            note.text = transactionView.note
            textAmount.text = transactionView.amount.toAmountFormat(withMinus = true)
            textCurrency.text =
                preferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)

            note.isSelected = true
            categoryName.isSelected = true
            cardName.isSelected = true
        }
    }


}