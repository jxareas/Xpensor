package com.jxareas.xpensor.ui.transactions.adapter

import android.content.SharedPreferences
import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.databinding.CardItemTransactionBinding
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.utils.setIcon
import com.jxareas.xpensor.utils.setTint

class TransactionViewHolder(
    private val binding: CardItemTransactionBinding,
    private val preferences: SharedPreferences,
) : TransactionAdapter.ViewHolder(binding) {

    override fun bind(item: Any) {
        val transactionView = item as TransactionView

        with(binding) {
            iconBackground.setTint(transactionView.iconColor)
            icon.setIcon(transactionView.icon)

            categoryName.text = transactionView.categoryName
            cardName.text = transactionView.accountName
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