package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

sealed class EditAccountUiEvent {
    object UpdateAccount : EditAccountUiEvent()
    data class UpdateCurrentColor(val color: String) : EditAccountUiEvent()
}
