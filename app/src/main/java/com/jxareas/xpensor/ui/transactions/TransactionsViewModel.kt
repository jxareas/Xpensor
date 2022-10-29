package com.jxareas.xpensor.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.DeleteTransactionUseCase
import com.jxareas.xpensor.domain.usecase.GetTransactionsUseCase
import com.jxareas.xpensor.domain.usecase.GetTransactionsWithDayUseCase
import com.jxareas.xpensor.ui.transactions.event.TransactionEvent
import com.jxareas.xpensor.ui.transactions.state.TransactionState
import com.jxareas.xpensor.utils.DateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getTransactionsWithDayUseCase: GetTransactionsWithDayUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
) : ViewModel() {

    private val _transactionState = MutableStateFlow<TransactionState>(TransactionState.Idle)
    val transactionState = _transactionState.asStateFlow()

    private val _events = MutableSharedFlow<TransactionEvent>()
    val events = _events.asSharedFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    private val selectedAccount get() = _selectedAccount.value

    private val _selectedDateRange = MutableStateFlow<DateRange>(null to null)
    private val selectedDateRange get() = _selectedDateRange.value

    private var getTransactionsJob: Job? = null

    init {
        launchGetTransactionsJob()
    }

    private fun launchGetTransactionsJob() {
        _transactionState.value = TransactionState.Loading
        getTransactionsJob?.cancel()

        getTransactionsJob = getTransactionsUseCase(selectedDateRange, selectedAccount)
            .onEach { transactions ->
                val transactionInformation =
                    getTransactionsWithDayUseCase(transactions, selectedDateRange, selectedAccount)
                _transactionState.value = TransactionState.Ready(transactionInformation)
            }.launchIn(viewModelScope)
    }

    suspend fun onDeleteTransaction(transaction: TransactionView) =
        deleteTransactionUseCase(transaction)

    fun onDateRangeUpdate(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchGetTransactionsJob()
    }

    fun onAccountUpdate(account: Account? = null) {
        _selectedAccount.value = account
        launchGetTransactionsJob()
    }

    fun onSelectedDateClick() {
        viewModelScope.launch {
            _events.emit(TransactionEvent.DateSelected)
        }
    }

    fun onAddTransactionClick(account: Account) {
        viewModelScope.launch {
            _events.emit(TransactionEvent.OpenTheAddTransactionSheet(account))
        }
    }

    fun onDeleteButtonClick(transaction: TransactionView) {
        viewModelScope.launch {
            _events.emit(TransactionEvent.ShowTheDeleteTransactionDialog(transaction))
        }
    }

    fun onDeleteTransactionConfirm(transaction: TransactionView) {
        viewModelScope.launch {
            _events.emit(TransactionEvent.DeleteTransaction(transaction))
        }
    }


}