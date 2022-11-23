package com.jxareas.xpensor.features.transactions.presentation.actions.add.state

sealed class AddTransactionState {
    object ValidTransaction : AddTransactionState()
    object InvalidTransaction : AddTransactionState()
}
