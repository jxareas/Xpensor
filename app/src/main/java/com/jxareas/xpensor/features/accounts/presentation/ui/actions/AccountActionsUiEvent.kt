package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

sealed class AccountActionsUiEvent {
    data class NavigateToEditAccountsScreen(val account: AccountUi) : AccountActionsUiEvent()
    data class ShowDeleteAccountDialog(val account: AccountUi) : AccountActionsUiEvent()
    object DeleteAccount : AccountActionsUiEvent()
}
