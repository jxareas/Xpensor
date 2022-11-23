package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem

sealed class AccountActionsEvent {
    data class NavigateToEditAccountsScreen(val account: AccountListItem) : AccountActionsEvent()
    data class ShowDeleteAccountDialog(val account: AccountListItem) : AccountActionsEvent()
    object DeleteAccount : AccountActionsEvent()
}