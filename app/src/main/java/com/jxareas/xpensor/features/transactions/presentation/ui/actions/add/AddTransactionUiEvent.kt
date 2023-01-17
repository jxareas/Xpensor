package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

sealed class AddTransactionUiEvent {
    object CreateNewTransaction : AddTransactionUiEvent()
}
