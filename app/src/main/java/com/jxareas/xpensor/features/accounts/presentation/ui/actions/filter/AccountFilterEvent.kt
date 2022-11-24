package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount

sealed class AccountFilterEvent {
    data class SelectAccount(val account: UiAccount) : AccountFilterEvent()
}