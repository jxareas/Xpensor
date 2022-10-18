package com.jxareas.xpensor.ui.accounts.events

import com.jxareas.xpensor.domain.model.Account

sealed class AccountEvent {
    object NavigateToAddAccountScreen : AccountEvent()
    data class OpenTheAccountActionsBottomSheet(val account: Account) : AccountEvent()
}