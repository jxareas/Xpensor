package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

sealed class AddTransactionState {
    object Idle : AddTransactionState()
    object Valid : AddTransactionState()
    object NotEnoughFunds : AddTransactionState()
}
