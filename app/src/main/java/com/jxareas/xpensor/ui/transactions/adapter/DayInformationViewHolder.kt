package com.jxareas.xpensor.ui.transactions.adapter

import android.content.SharedPreferences
import com.jxareas.xpensor.data.local.views.DayInformationView
import com.jxareas.xpensor.databinding.ListItemDayInformationBinding
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY

class DayInformationViewHolder(
    private val binding: ListItemDayInformationBinding,
    private val preferences: SharedPreferences,
) : TransactionAdapter.ViewHolder(binding) {


    override fun bind(item: Any) {
        val dayInfo = item as DayInformationView

        val dateValue = dayInfo.transactionDate
        val amountValue = dayInfo.amountPerDay
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