package com.jxareas.xpensor.features.converter.presentation.ui

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

sealed class CurrencyConverterEvent {
    object Convert : CurrencyConverterEvent()
    object Swap : CurrencyConverterEvent()
    data class OpenTheAddTransactionSheet(val account: AccountWithDetails, val amount: Float) : CurrencyConverterEvent()
}