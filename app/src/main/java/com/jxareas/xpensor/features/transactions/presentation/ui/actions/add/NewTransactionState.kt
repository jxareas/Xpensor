package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

sealed class NewTransactionState {
    object Idle : NewTransactionState()
    object Valid : NewTransactionState()
    object NotEnoughFunds : NewTransactionState()
}
