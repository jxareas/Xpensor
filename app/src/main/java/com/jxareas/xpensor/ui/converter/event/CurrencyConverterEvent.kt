package com.jxareas.xpensor.ui.converter.event

import com.jxareas.xpensor.domain.model.AccountWithDetails

sealed class CurrencyConverterEvent {
    object Convert : CurrencyConverterEvent()
    object Swap : CurrencyConverterEvent()
    data class OpenTheAddTransactionSheet(val account: AccountWithDetails, val amount: Float) : CurrencyConverterEvent()
}