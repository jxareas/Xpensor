package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

sealed class AccountActionsEvent {
    data class NavigateToEditAccountsScreen(val account: AccountUi) : AccountActionsEvent()
    data class ShowDeleteAccountDialog(val account: AccountUi) : AccountActionsEvent()
    object DeleteAccount : AccountActionsEvent()
}
