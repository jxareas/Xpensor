package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

sealed class EditAccountEvent {
    object UpdateAccount : EditAccountEvent()
    data class UpdateCurrentColor(val color: String) : EditAccountEvent()
}