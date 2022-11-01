package com.jxareas.xpensor.ui.accounts.actions.filter.events

import com.jxareas.xpensor.domain.model.Account

sealed class AccountFilterEvent {
    data class SelectAccount(val account: Account) : AccountFilterEvent()
}