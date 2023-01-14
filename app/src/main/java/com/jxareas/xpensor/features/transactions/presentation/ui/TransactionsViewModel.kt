package com.jxareas.xpensor.features.transactions.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccount
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.date.domain.model.DateRange
import com.jxareas.xpensor.features.date.domain.model.EmptyDateRange
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.domain.usecase.DeleteTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsWithDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getTransactionsWithDayUseCase: GetTransactionsWithDayUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
) : ViewModel() {

    private val _transactionState = MutableStateFlow<TransactionState>(TransactionState.Idle)
    val transactionState = _transactionState.asStateFlow()

    private val _eventEmitter = Channel<TransactionEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    private val _selectedAccount = MutableStateFlow<AccountUi?>(null)
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

        val account = selectedAccount?.toAccount()

        getTransactionsJob = getTransactionsUseCase.invoke(selectedDateRange, account)
            .onEach { transactions ->
                val transactionInformation =
                    getTransactionsWithDayUseCase.invoke(transactions, selectedDateRange, account)
                _transactionState.value = TransactionState.Ready(transactionInformation)
            }
            .launchIn(viewModelScope)
    }

    fun deleteTransaction(transactionDetails: TransactionDetails) = launchScoped {
        deleteTransactionUseCase.invoke(transactionDetails)
    }

    fun onUpdateSelectedDateRange(dateRange: DateRange = EmptyDateRange) {
        val (initialDate, finalDate) = dateRange
        _selectedDateRange.value = initialDate to finalDate
        launchGetTransactionsJob()
    }

    fun onUpdateSelectedAccount(accountUi: AccountUi? = null) {
        _selectedAccount.value = accountUi
        launchGetTransactionsJob()
    }

    fun onSelectedDateClick() = launchScoped {
        _eventEmitter.send(TransactionEvent.DateSelected)
    }

    fun onAddTransactionClick(accountUi: AccountUi) = launchScoped {
        _eventEmitter.send(TransactionEvent.OpenTheAddTransactionSheet(accountUi))
    }

    fun onDeleteButtonClick(transactionDetails: TransactionDetails) = launchScoped {
        _eventEmitter.send(TransactionEvent.ShowTheDeleteTransactionDialog(transactionDetails))
    }

    fun onDeleteTransactionConfirm(transactionDetails: TransactionDetails) = launchScoped {
        _eventEmitter.send(TransactionEvent.DeleteTransaction(transactionDetails))
    }
}
