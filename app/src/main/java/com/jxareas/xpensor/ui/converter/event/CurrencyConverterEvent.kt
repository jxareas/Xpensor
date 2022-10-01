package com.jxareas.xpensor.ui.converter.event

import com.jxareas.xpensor.domain.model.Account

sealed class CurrencyConverterEvent {
    object Convert : CurrencyConverterEvent()
    object Swap : CurrencyConverterEvent()
    data class OpenTheAddTransactionSheet(val account: Account, val amount: Float) : CurrencyConverterEvent()
}