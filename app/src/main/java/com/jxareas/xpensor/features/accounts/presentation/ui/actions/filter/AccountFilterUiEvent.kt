package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi

sealed class AccountFilterUiEvent {
    data class SelectAccount(val account: AccountWithDetailsUi) : AccountFilterUiEvent()
}
