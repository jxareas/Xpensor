package com.jxareas.xpensor.features.transactions.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.DateRange
import com.jxareas.xpensor.features.accounts.presentation.mapper.AccountUiMapper
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.domain.usecase.DeleteTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsWithDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getTransactionsWithDayUseCase: GetTransactionsWithDayUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val accountUiMapper: AccountUiMapper,
) : ViewModel() {

    private val _transactionState = MutableStateFlow<TransactionState>(TransactionState.Idle)
    val transactionState = _transactionState.asStateFlow()

    private val _events = MutableSharedFlow<TransactionEvent>()
    val events = _events.asSharedFlow()

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

        val account = selectedAccount?.let { accountListItem ->
            accountUiMapper.mapToDomain(accountListItem)
        }

        getTransactionsJob = getTransactionsUseCase(selectedDateRange, account)
            .onEach { transactions ->
                val transactionInformation =
                    getTransactionsWithDayUseCase(transactions, selectedDateRange, account)
                _transactionState.value = TransactionState.Ready(transactionInformation)
            }
            .launchIn(viewModelScope)
    }

    suspend fun onDeleteTransaction(transaction: TransactionView) =
        deleteTransactionUseCase(transaction)

    fun onUpdateSelectedDateRange(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchGetTransactionsJob()
    }

    fun onUpdateSelectedAccount(account: AccountUi? = null) {
        _selectedAccount.value = account
        launchGetTransactionsJob()
    }

    fun onSelectedDateClick() = launchScoped {
        _events.emit(TransactionEvent.DateSelected)
    }

    fun onAddTransactionClick(account: AccountUi) = launchScoped {
        _events.emit(TransactionEvent.OpenTheAddTransactionSheet(account))
    }

    fun onDeleteButtonClick(transaction: TransactionView) = launchScoped {
        _events.emit(TransactionEvent.ShowTheDeleteTransactionDialog(transaction))
    }

    fun onDeleteTransactionConfirm(transaction: TransactionView) = launchScoped {
        _events.emit(TransactionEvent.DeleteTransaction(transaction))
    }
}
