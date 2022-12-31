package com.jxareas.xpensor.features.converter.presentation.ui

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

sealed class ConvertCurrencyEvent {
    object Convert : ConvertCurrencyEvent()
    object Swap : ConvertCurrencyEvent()
    data class OpenTheAddTransactionSheet(val account: AccountWithDetails, val amount: Float) :
        ConvertCurrencyEvent()
}
