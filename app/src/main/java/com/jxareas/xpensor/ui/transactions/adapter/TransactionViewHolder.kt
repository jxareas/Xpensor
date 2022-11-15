package com.jxareas.xpensor.ui.transactions.adapter

import android.content.SharedPreferences
import com.jxareas.xpensor.databinding.CardItemTransactionBinding
import com.jxareas.xpensor.domain.model.TransactionWithDetails
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.utils.extensions.setIcon
import com.jxareas.xpensor.utils.extensions.setTint

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