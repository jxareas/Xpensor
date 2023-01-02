package com.jxareas.xpensor.features.chart.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.common.utils.DateRange
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.features.transactions.presentation.mapper.asCategoryWithAmount
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
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
class ChartViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryWithAmountUi>())
    val categories = _categories.asStateFlow()

    private val _events = MutableSharedFlow<ChartUiEvent>()
    val events = _events.asSharedFlow()

    private var fetchCategoriesJob: Job? = null

    private val _selectedAccountUi = MutableStateFlow<AccountUi?>(null)

    private val _selectedDateRange = MutableStateFlow<DateRange>(null to null)

    init {
        launchFetchCategoriesJob()
    }

    private fun launchFetchCategoriesJob() {
        fetchCategoriesJob?.cancel()
        val selectedAccount: AccountWithDetails? =
            _selectedAccountUi.value?.let(AccountUi::asAccountWithDetails)
        fetchCategoriesJob =
            getCategoriesUseCase(_selectedDateRange.value, selectedAccount)
                .mapList(CategoryWithDetails::asCategoryWithAmount)
                .onEach(_categories::value::set)
                .launchIn(viewModelScope)
    }

    fun onSelectedDateClick() = launchScoped {
        _events.emit(ChartUiEvent.DateSelected)
    }

    fun onUpdateSelectedDateRange(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchFetchCategoriesJob()
    }

    fun onUpdateSelectedAccount(account: AccountUi? = null) {
        _selectedAccountUi.value = account
        launchFetchCategoriesJob()
    }
}
