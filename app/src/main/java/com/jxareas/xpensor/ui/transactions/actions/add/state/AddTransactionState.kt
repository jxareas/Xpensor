package com.jxareas.xpensor.ui.transactions.actions.add.state

sealed class AddTransactionState {
    object ValidTransaction : AddTransactionState()
    object InvalidTransaction : AddTransactionState()
}
