package com.jxareas.xpensor.ui.accounts.actions.add.events

sealed class AddAccountEvent {
    object CreateNewAccount : AddAccountEvent()
    data class SelectAccountColor(val color: String) : AddAccountEvent()
}
