package com.jxareas.xpensor.features.transactions.presentation.ui

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails

sealed class TransactionUiEvent {
    object DateSelected : TransactionUiEvent()
    data class OpenTheAddTransactionSheet(val account: AccountUi) : TransactionUiEvent()
    data class ShowTheDeleteTransactionDialog(val transaction: TransactionDetails) : TransactionUiEvent()
    data class DeleteTransaction(val transaction: TransactionDetails) : TransactionUiEvent()
}
