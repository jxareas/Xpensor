package com.jxareas.xpensor.features.transactions.presentation

import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView

sealed class TransactionEvent {
    object DateSelected : TransactionEvent()
    data class OpenTheAddTransactionSheet(val account: AccountListItem) : TransactionEvent()
    data class ShowTheDeleteTransactionDialog(val transaction: TransactionView) : TransactionEvent()
    data class DeleteTransaction(val transaction: TransactionView) : TransactionEvent()

}