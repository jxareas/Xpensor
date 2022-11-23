package com.jxareas.xpensor.features.accounts.presentation.ui

import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem

sealed class AccountEvent {
    object NavigateToAddAccountScreen : AccountEvent()
    data class OpenTheAccountBottomSheet(val account: AccountListItem) : AccountEvent()
}