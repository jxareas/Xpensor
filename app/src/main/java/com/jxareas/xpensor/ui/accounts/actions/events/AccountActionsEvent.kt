package com.jxareas.xpensor.ui.accounts.actions.events

import com.jxareas.xpensor.domain.model.Account

sealed class AccountActionsEvent {
    data class NavigateToEditAccountsScreen(val account: Account) : AccountActionsEvent()
    data class ShowDeleteAccountDialog(val account: Account) : AccountActionsEvent()
    object DeleteAccount : AccountActionsEvent()
}