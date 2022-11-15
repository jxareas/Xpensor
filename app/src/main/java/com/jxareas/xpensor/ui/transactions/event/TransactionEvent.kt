package com.jxareas.xpensor.ui.transactions.event

import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.domain.model.AccountWithDetails

sealed class TransactionEvent {
    object DateSelected : TransactionEvent()
    data class OpenTheAddTransactionSheet(val account: AccountWithDetails) : TransactionEvent()
    data class ShowTheDeleteTransactionDialog(val transaction: TransactionView) : TransactionEvent()
    data class DeleteTransaction(val transaction: TransactionView) : TransactionEvent()

}