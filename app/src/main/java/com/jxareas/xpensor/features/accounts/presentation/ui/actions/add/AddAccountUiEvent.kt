package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

sealed class AddAccountUiEvent {
    object CreateNewAccount : AddAccountUiEvent()
    data class SelectAccountColor(val color: String) : AddAccountUiEvent()
}
