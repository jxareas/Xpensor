package com.jxareas.xpensor.features.accounts.presentation.actions.events

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

sealed class AccountActionsEvent {
    data class NavigateToEditAccountsScreen(val account: AccountWithDetails) : AccountActionsEvent()
    data class ShowDeleteAccountDialog(val account: AccountWithDetails) : AccountActionsEvent()
    object DeleteAccount : AccountActionsEvent()
}