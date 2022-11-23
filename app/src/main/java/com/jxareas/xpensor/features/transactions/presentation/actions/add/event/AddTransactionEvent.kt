package com.jxareas.xpensor.features.transactions.presentation.actions.add.event

sealed class AddTransactionEvent {
    object CreateNewTransaction : AddTransactionEvent()
}