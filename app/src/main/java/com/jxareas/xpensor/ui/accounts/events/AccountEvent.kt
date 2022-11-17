package com.jxareas.xpensor.ui.accounts.events

import com.jxareas.xpensor.domain.model.AccountWithDetails

sealed class AccountEvent {
    object NavigateToAddAccountScreen : AccountEvent()
    data class OpenTheAccountBottomSheet(val account: AccountWithDetails) : AccountEvent()
}