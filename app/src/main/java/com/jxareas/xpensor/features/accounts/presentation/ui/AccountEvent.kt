package com.jxareas.xpensor.features.accounts.presentation.ui

import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount

sealed class AccountEvent {
    object NavigateToAddAccountScreen : AccountEvent()
    data class OpenTheAccountBottomSheet(val account: UiAccount) : AccountEvent()
}