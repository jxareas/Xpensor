package com.jxareas.xpensor.ui.transactions.event

import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.domain.model.Account

sealed class TransactionEvent {
    object SelectDate : TransactionEvent()
    data class OpenTheAddTransactionSheet(val account: Account) : TransactionEvent()
    data class ShowTheDeleteTransactionDialog(val transaction: TransactionView) : TransactionEvent()
    data class DeleteTransaction(val transaction: TransactionView) : TransactionEvent()

}