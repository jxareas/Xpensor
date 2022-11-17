package com.jxareas.xpensor.ui.accounts.actions.events

import com.jxareas.xpensor.domain.model.AccountWithDetails

sealed class AccountActionsEvent {
    data class NavigateToEditAccountsScreen(val account: AccountWithDetails) : AccountActionsEvent()
    data class ShowDeleteAccountDialog(val account: AccountWithDetails) : AccountActionsEvent()
    object DeleteAccount : AccountActionsEvent()
}