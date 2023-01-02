package com.jxareas.xpensor.features.accounts.presentation.ui

import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi

sealed class AccountUiEvent {
    object NavigateToAddAccountScreen : AccountUiEvent()
    data class OpenAccountBottomSheet(val account: AccountWithDetailsUi) : AccountUiEvent()
}
