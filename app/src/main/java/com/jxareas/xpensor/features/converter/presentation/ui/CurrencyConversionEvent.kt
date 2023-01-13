package com.jxareas.xpensor.features.converter.presentation.ui

import com.jxareas.xpensor.features.accounts.domain.model.Account

sealed class CurrencyConversionEvent {
    object Convert : CurrencyConversionEvent()
    object Swap : CurrencyConversionEvent()
    data class OpenTheAddTransactionSheet(val account: Account, val amount: Float) :
        CurrencyConversionEvent()
}
