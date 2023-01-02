package com.jxareas.xpensor.features.transactions.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.DateRange
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import com.jxareas.xpensor.features.transactions.domain.usecase.DeleteTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsWithDayUseCase
import com.jxareas.xpensor.features.transactions.presentation.mapper.asTransactionWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.TransactionDetailsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _event = MutableSharedFlow<TransactionUiEvent>()
    val event = _event.asSharedFlow()

    private val _selectedAccount = MutableStateFlow<AccountWithDetailsUi?>(null)
    private val selectedAccount get() = _selectedAccount.value

    private val _selectedDateRange = MutableStateFlow<DateRange>(null to null)
    private val selectedDateRange get() = _selectedDateRange.value

    private var fetchTransactionsJob: Job? = null

    init {
        launchGetTransactionsJob()
    }

    private fun launchGetTransactionsJob() {
        _transactionState.value = TransactionState.Loading
        fetchTransactionsJob?.cancel()

        val account = selectedAccount?.let(AccountWithDetailsUi::asAccountWithDetails)
        fetchTransactionsJob = getTransactionsUseCase(selectedDateRange, account)
            .onEach { transactions ->
                val transactionInformation =
                    getTransactionsWithDayUseCase(transactions, selectedDateRange, account)
                _transactionState.value = TransactionState.Ready(transactionInformation)
            }
            .launchIn(viewModelScope)
    }

    fun deleteTransaction(transaction: TransactionDetailsUi) = launchScoped {
        deleteTransactionUseCase(transaction.asTransactionWithDetails())
    }

    fun onUpdateSelectedDateRange(initialDate: LocalDate? = null, FinalDate: LocalDate? = null) {
        _selectedDateRange.value = initialDate to FinalDate
        launchGetTransactionsJob()
    }

    fun onUpdateSelectedAccount(account: AccountWithDetailsUi? = null) {
        _selectedAccount.value = account
        launchGetTransactionsJob()
    }

    fun onSelectedDate() = launchScoped {
        _event.emit(TransactionUiEvent.DateSelected)
    }

    fun onAddNewTransaction(account: AccountWithDetailsUi) = launchScoped {
        _event.emit(TransactionUiEvent.OpenTheAddTransactionSheet(account))
    }

    fun onDeleteTransaction(transaction: TransactionDetailsUi) = launchScoped {
        _event.emit(TransactionUiEvent.ShowDeleteTransactionDialog(transaction))
    }

    fun onConfirmTransactionDeletion(transaction: TransactionDetailsUi) = launchScoped {
        _event.emit(TransactionUiEvent.DeleteTransaction(transaction))
    }
}
