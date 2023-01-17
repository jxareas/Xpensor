package com.jxareas.xpensor.features.transactions.presentation.ui.adapter

import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.databinding.ListItemDayInformationBinding
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay

class TransactionsByDayViewHolder(
    private val binding: ListItemDayInformationBinding,
    private val userPreferences: UserPreferences,
) : TransactionAdapter.ViewHolder(binding) {

    override fun bind(item: Any) {
        val dayInfo = item as TransactionByDay

        val dateValue = dayInfo.date
        val amountValue = dayInfo.amount
        val monthAndYearValue = "${dateValue.month} ${dateValue.year}"

        with(binding) {
            amount.text = amountValue.toAmountFormat(withMinus = true)
            currency.text = userPreferences.getPreferredCurrencyName()

            day.text = dateValue.dayOfMonth.toString()
            monthAndYear.text = monthAndYearValue
            dayOfWeek.text = dateValue.dayOfWeek.name
        }
    }
}
