package com.jxareas.xpensor.features.accounts.presentation.actions.edit.events

sealed class EditAccountEvent {
    object UpdateAccount : EditAccountEvent()
    data class UpdateCurrentColor(val color: String) : EditAccountEvent()
}