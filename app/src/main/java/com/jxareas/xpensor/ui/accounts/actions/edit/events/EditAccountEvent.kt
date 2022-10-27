package com.jxareas.xpensor.ui.accounts.actions.edit.events

sealed class EditAccountEvent {
    object UpdateAccount : EditAccountEvent()
    data class SelectCurrentAccountColor(val color: String) : EditAccountEvent()
}