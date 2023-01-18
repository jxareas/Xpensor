package com.jxareas.xpensor.features.converter.presentation.ui

import com.jxareas.xpensor.features.accounts.domain.model.Account

sealed class ConvertCurrencyUiEvent {
    object Convert : ConvertCurrencyUiEvent()
    object Swap : ConvertCurrencyUiEvent()
    data class OpenTheAddTransactionSheet(val account: Account, val amount: Float) :
        ConvertCurrencyUiEvent()
}
