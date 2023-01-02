package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi

sealed class AccountActionsUiEvent {
    data class NavigateToEditAccountsScreen(val account: AccountWithDetailsUi) : AccountActionsUiEvent()
    data class ShowDeleteAccountDialog(val account: AccountWithDetailsUi) : AccountActionsUiEvent()
    object DeleteAccount : AccountActionsUiEvent()
}
