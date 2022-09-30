package com.jxareas.xpensor.ui.converter.event

import com.jxareas.xpensor.domain.model.Account

sealed class CurrencyEvent {
    object Convert : CurrencyEvent()
    object Swap : CurrencyEvent()
    data class OpenTheAddTransactionSheet(val account: Account, val amount: Float) : CurrencyEvent()
}