package com.jxareas.xpensor.features.transactions.presentation.ui

import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import com.jxareas.xpensor.features.transactions.presentation.model.TransactionDetailsUi

sealed class TransactionUiEvent {
    object DateSelected : TransactionUiEvent()
    data class OpenTheAddTransactionSheet(val account: AccountWithDetailsUi) : TransactionUiEvent()
    data class ShowDeleteTransactionDialog(val transaction: TransactionDetailsUi) : TransactionUiEvent()
    data class DeleteTransaction(val transaction: TransactionDetailsUi) : TransactionUiEvent()
}
