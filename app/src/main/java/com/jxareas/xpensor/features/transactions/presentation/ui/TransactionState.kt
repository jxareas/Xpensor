package com.jxareas.xpensor.features.transactions.presentation.ui

sealed class TransactionState {
    data class Ready(val transactions: List<Any>) : TransactionState()
    object Loading : TransactionState()
    object Idle : TransactionState()
}
