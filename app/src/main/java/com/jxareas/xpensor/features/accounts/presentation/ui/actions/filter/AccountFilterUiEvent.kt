package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

sealed class AccountFilterUiEvent {
    data class SelectAccount(val account: AccountUi) : AccountFilterUiEvent()
}
