package com.jxareas.xpensor.features.accounts.presentation.actions.add.events

sealed class AddAccountEvent {
    object CreateNewAccount : AddAccountEvent()
    data class SelectAccountColor(val color: String) : AddAccountEvent()
}
