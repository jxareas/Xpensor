package com.jxareas.xpensor.features.transactions.presentation.ui.adapter

import com.jxareas.xpensor.common.extensions.setIcon
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.databinding.CardItemTransactionBinding
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails

class TransactionViewHolder(
    private val binding: CardItemTransactionBinding,
    private val userPreferences: UserPreferences,
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
            textCurrency.text = userPreferences.getPreferredCurrencyName()

            note.isSelected = true
            categoryName.isSelected = true
            cardName.isSelected = true
        }
    }
}
