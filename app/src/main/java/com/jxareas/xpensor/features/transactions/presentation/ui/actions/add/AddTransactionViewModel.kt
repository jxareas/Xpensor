package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.usecase.AddTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.ValidateTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val validateTransactionUseCase: ValidateTransactionUseCase,
) : ViewModel() {

    private val _transactionState =
        MutableStateFlow<AddTransactionState>(AddTransactionState.Idle)
    val transactionState = _transactionState.asStateFlow()

    private val _eventEmitter = MutableSharedFlow<AddTransactionEvent>()
    val eventSource = _eventEmitter.asSharedFlow()

    fun onAddTransaction(transaction: Transaction, accountId: Int, categoryId: Int) = launchScoped {
        val isTransactionValid = validateTransactionUseCase.invoke(transaction, accountId)
        if (isTransactionValid)
            addTransactionUseCase.invoke(transaction, accountId, categoryId).also {
                _transactionState.emit(AddTransactionState.Valid)
            }
        else _transactionState.emit(AddTransactionState.NotEnoughFunds)
    }

    fun onConfirmTransactionCreation() = launchScoped {
        _eventEmitter.emit(AddTransactionEvent.CreateNewTransaction)
    }
}
