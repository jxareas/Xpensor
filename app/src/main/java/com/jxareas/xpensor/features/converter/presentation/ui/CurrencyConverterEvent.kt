package com.jxareas.xpensor.features.converter.presentation.ui

import com.jxareas.xpensor.features.accounts.domain.model.Account

sealed class CurrencyConverterEvent {
    object Convert : CurrencyConverterEvent()
    object Swap : CurrencyConverterEvent()
    data class OpenTheAddTransactionSheet(val account: Account, val amount: Float) :
        CurrencyConverterEvent()
}
