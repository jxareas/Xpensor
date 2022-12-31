package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

sealed class AddTransactionEvent {
    object CreateNewTransaction : AddTransactionEvent()
}
