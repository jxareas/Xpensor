package com.jxareas.xpensor.features.transactions.presentation.ui.adapter

import android.content.SharedPreferences
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.databinding.ListItemDayInformationBinding
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay

class TransactionsByDayViewHolder(
    private val binding: ListItemDayInformationBinding,
    private val preferences: SharedPreferences,
) : TransactionAdapter.ViewHolder(binding) {

    override fun bind(item: Any) {
        val dayInfo = item as TransactionAmountPerDay

        val dateValue = dayInfo.date
        val amountValue = dayInfo.amount
        val monthAndYearValue = "${dateValue.month} ${dateValue.year}"

        with(binding) {
            amount.text = amountValue.toAmountFormat(withMinus = true)
            currency.text = preferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY)

            day.text = dateValue.dayOfMonth.toString()
            monthAndYear.text = monthAndYearValue
            dayOfWeek.text = dateValue.dayOfWeek.name
        }
    }
}
