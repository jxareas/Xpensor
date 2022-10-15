package com.jxareas.xpensor.ui.transactions.state

sealed class TransactionState {
    data class Ready(val transactions: List<Any>) : TransactionState()
    object Loading : TransactionState()
    object Idle : TransactionState()
}