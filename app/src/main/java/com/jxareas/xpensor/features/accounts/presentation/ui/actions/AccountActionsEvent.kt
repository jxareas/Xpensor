package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount

sealed class AccountActionsEvent {
    data class NavigateToEditAccountsScreen(val account: UiAccount) : AccountActionsEvent()
    data class ShowDeleteAccountDialog(val account: UiAccount) : AccountActionsEvent()
    object DeleteAccount : AccountActionsEvent()
}