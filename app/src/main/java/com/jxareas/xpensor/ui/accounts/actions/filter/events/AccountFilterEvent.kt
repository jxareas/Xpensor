package com.jxareas.xpensor.ui.accounts.actions.filter.events

import com.jxareas.xpensor.domain.model.AccountWithDetails

sealed class AccountFilterEvent {
    data class SelectAccount(val account: AccountWithDetails) : AccountFilterEvent()
}