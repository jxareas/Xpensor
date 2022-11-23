package com.jxareas.xpensor.features.accounts.presentation.actions.filter.events

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

sealed class AccountFilterEvent {
    data class SelectAccount(val account: AccountWithDetails) : AccountFilterEvent()
}