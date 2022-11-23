package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem

sealed class AccountFilterEvent {
    data class SelectAccount(val account: AccountListItem) : AccountFilterEvent()
}