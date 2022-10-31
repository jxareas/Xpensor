package com.jxareas.xpensor.ui.transactions.actions.add

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.domain.model.Transaction
import com.jxareas.xpensor.domain.usecase.AddTransactionUseCase
import com.jxareas.xpensor.domain.usecase.ValidateTransactionUseCase
import com.jxareas.xpensor.ui.transactions.actions.add.event.AddTransactionEvent
import com.jxareas.xpensor.ui.transactions.actions.add.state.AddTransactionState
import com.jxareas.xpensor.utils.launchScoped
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

    suspend fun onAddTransaction(transaction: Transaction) {
        val isTransactionValid = validateTransactionUseCase(transaction)
        if (isTransactionValid)
            addTransactionUseCase(transaction).also {
                _transactionState.emit(AddTransactionState.ValidTransaction)
            }
        else _transactionState.emit(AddTransactionState.InvalidTransaction)
    }

    fun onApplyChanges() = launchScoped {
        _events.emit(AddTransactionEvent.CreateNewTransaction)
    }

}