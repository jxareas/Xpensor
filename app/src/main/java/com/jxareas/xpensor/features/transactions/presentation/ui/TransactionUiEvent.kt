package com.jxareas.xpensor.features.transactions.presentation.ui

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView

sealed class TransactionUiEvent {
    object DateSelected : TransactionUiEvent()
    data class OpenTheAddTransactionSheet(val account: AccountUi) : TransactionUiEvent()
    data class ShowTheDeleteTransactionDialog(val transaction: TransactionView) : TransactionUiEvent()
    data class DeleteTransaction(val transaction: TransactionView) : TransactionUiEvent()
}
