package com.jxareas.xpensor.ui.accounts.actions.edit.events

sealed class EditAccountEvent {
    object UpdateAccount : EditAccountEvent()
    data class UpdateCurrentColor(val color: String) : EditAccountEvent()
}