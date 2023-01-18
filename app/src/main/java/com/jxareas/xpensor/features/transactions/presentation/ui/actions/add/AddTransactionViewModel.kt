package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.usecase.AddTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.ValidateTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val validateTransactionUseCase: ValidateTransactionUseCase,
) : ViewModel() {

    private val _transactionState =
        MutableStateFlow<NewTransactionState>(NewTransactionState.Idle)
    val transactionState = _transactionState.asStateFlow()

    private val _eventEmitter = Channel<AddTransactionUiEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    fun onAddTransaction(transaction: Transaction, accountId: Int, categoryId: Int) = launchScoped {
        val isTransactionValid = validateTransactionUseCase.invoke(transaction, accountId)
        if (isTransactionValid)
            addTransactionUseCase.invoke(transaction, accountId, categoryId).also {
                _transactionState.emit(NewTransactionState.Valid)
            }
        else _transactionState.emit(NewTransactionState.NotEnoughFunds)
    }

    fun onConfirmTransactionCreation() = launchScoped {
        _eventEmitter.send(AddTransactionUiEvent.CreateNewTransaction)
    }
}
