package com.jxareas.xpensor.features.accounts.presentation.ui

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

sealed class AccountUiEvent {
    object NavigateToAddAccountScreen : AccountUiEvent()
    data class OpenTheAccountBottomSheet(val account: AccountUi) : AccountUiEvent()
}
