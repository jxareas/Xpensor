package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add.event

sealed class AddTransactionEvent {
    object CreateNewTransaction : AddTransactionEvent()
}