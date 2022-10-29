package com.jxareas.xpensor.ui.transactions.actions.add.event

sealed class AddTransactionEvent {
    object CreateNewTransaction : AddTransactionEvent()
}