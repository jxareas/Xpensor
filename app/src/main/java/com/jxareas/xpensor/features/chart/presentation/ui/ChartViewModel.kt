package com.jxareas.xpensor.features.chart.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.mapper.AccountUiMapper
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
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
    private val accountUiMapper: AccountUiMapper,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryWithDetails>())
    val categories = _categories.asStateFlow()

    private val _events = MutableSharedFlow<ChartEvent>()
    val events = _events.asSharedFlow()

    private var getCategoriesJob: Job? = null

    private val _selectedAccountUi = MutableStateFlow<AccountUi?>(null)

    private val _selectedDateRange = MutableStateFlow<Pair<LocalDate?, LocalDate?>>(null to null)

    init {
        launchGetCategoriesJob()
    }

    private fun launchGetCategoriesJob() {
        getCategoriesJob?.cancel()
        val selectedAccount: AccountWithDetails? =
            _selectedAccountUi.value?.let { accountListItem ->
                accountUiMapper.mapToDomain(accountListItem)
            }
        getCategoriesJob =
            getCategoriesUseCase(_selectedDateRange.value, selectedAccount)
                .onEach { categories -> _categories.value = categories }
                .launchIn(viewModelScope)
    }

    fun onSelectedDateClick() = launchScoped {
        _events.emit(ChartEvent.DateSelected)
    }

    fun onUpdateSelectedDateRange(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchGetCategoriesJob()
    }

    fun onUpdateSelectedAccount(account: AccountUi? = null) {
        _selectedAccountUi.value = account
        launchGetCategoriesJob()
    }


}