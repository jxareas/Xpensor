package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

sealed class AddTransactionState {
    object ValidTransaction : AddTransactionState()
    object InvalidTransaction : AddTransactionState()
}
