package com.jxareas.xpensor.features.converter.presentation.ui

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

sealed class ConvertCurrencyUiEvent {
    object Convert : ConvertCurrencyUiEvent()
    object Swap : ConvertCurrencyUiEvent()
    data class OpenTheAddTransactionSheet(val account: AccountWithDetails, val amount: Float) :
        ConvertCurrencyUiEvent()
}
