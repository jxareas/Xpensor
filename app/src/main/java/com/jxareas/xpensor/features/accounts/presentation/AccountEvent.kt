package com.jxareas.xpensor.features.accounts.presentation

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

sealed class AccountEvent {
    object NavigateToAddAccountScreen : AccountEvent()
    data class OpenTheAccountBottomSheet(val account: AccountWithDetails) : AccountEvent()
}