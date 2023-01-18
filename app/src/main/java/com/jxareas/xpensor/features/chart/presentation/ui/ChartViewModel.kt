package com.jxareas.xpensor.features.chart.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccount
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.date.domain.model.DateRange
import com.jxareas.xpensor.features.date.domain.model.EmptyDateRange
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.features.transactions.presentation.mapper.toCategoryWithAmountUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
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
class ChartViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryWithAmountUi>())
    val categories = _categories.asStateFlow()

    private val _eventEmitter = Channel<ChartUiEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    private var fetchCategoriesJob: Job? = null

    private val _selectedAccountUi = MutableStateFlow<AccountUi?>(null)

    private val _selectedDateRange = MutableStateFlow(EmptyDateRange)

    init {
        launchFetchCategoriesJob()
    }

    private fun launchFetchCategoriesJob() {
        fetchCategoriesJob?.cancel()
        val selectedAccount = _selectedAccountUi.value?.toAccount()

        fetchCategoriesJob =
            getCategoriesUseCase.invoke(_selectedDateRange.value, selectedAccount)
                .mapEach(CategoryWithDetails::toCategoryWithAmountUi)
                .onEach(_categories::value::set)
                .launchIn(viewModelScope)
    }

    fun onDateSelectedClick() = launchScoped {
        _eventEmitter.send(ChartUiEvent.DateSelected)
    }

    fun onSelectedDateRangeUpdate(dateRange: DateRange = EmptyDateRange) {
        val (initialDate, finalDate) = dateRange
        _selectedDateRange.value = initialDate to finalDate
        launchFetchCategoriesJob()
    }

    fun onSelectedAccountUpdate(account: AccountUi? = null) {
        _selectedAccountUi.value = account
        launchFetchCategoriesJob()
    }
}
