package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

sealed class AddAccountEvent {
    object CreateNewAccount : AddAccountEvent()
    data class SelectAccountColor(val color: String) : AddAccountEvent()
}
