package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.usecase.AddTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.ValidateTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val validateTransactionUseCase: ValidateTransactionUseCase,
) : ViewModel() {

    private val _transactionState = MutableSharedFlow<AddTransactionState>()
    val transactionState = _transactionState.asSharedFlow()

    private val _events = MutableSharedFlow<AddTransactionEvent>()
    val events = _events.asSharedFlow()

    fun onAddTransaction(transaction: Transaction, accountId: Int, categoryId: Int) = launchScoped {
        val isTransactionValid = validateTransactionUseCase.invoke(transaction, accountId)
        if (isTransactionValid)
            addTransactionUseCase.invoke(transaction, accountId, categoryId).also {
                _transactionState.emit(AddTransactionState.ValidTransaction)
            }
        else _transactionState.emit(AddTransactionState.InvalidTransaction)
    }

    fun onConfirmTransactionCreation() = launchScoped {
        _events.emit(AddTransactionEvent.CreateNewTransaction)
    }
}
