package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

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
class SelectCategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryWithAmountUi>())
    val categories = _categories.asStateFlow()

    private val _eventEmitter = Channel<CategorySelectionEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    private val _selectedAccount = MutableStateFlow<AccountUi?>(null)
    private val _selectedDateRange = MutableStateFlow(EmptyDateRange)

    private var fetchCategoriesJob: Job? = null

    init {
        launchFetchCategoriesJob()
    }

    private fun launchFetchCategoriesJob() {
        fetchCategoriesJob?.cancel()
        val account = _selectedAccount.value?.toAccount()

        fetchCategoriesJob =
            getCategoriesUseCase.invoke(_selectedDateRange.value, account)
                .mapEach(CategoryWithDetails::toCategoryWithAmountUi)
                .onEach(_categories::value::set)
                .launchIn(viewModelScope)
    }

    fun onUpdateSelectedDateRange(dateRange: DateRange = EmptyDateRange) {
        val (initialDate, finalDate) = dateRange
        _selectedDateRange.value = initialDate to finalDate
        launchFetchCategoriesJob()
    }

    fun onUpdateSelectedAccount(accountUi: AccountUi? = null) {
        _selectedAccount.value = accountUi
        launchFetchCategoriesJob()
    }

    fun onSelectCategoryClick(
        accountUi: AccountUi,
        categoryWithAmountUi: CategoryWithAmountUi,
    ) = launchScoped {
        _eventEmitter.send(CategorySelectionEvent.SelectCategory(accountUi, categoryWithAmountUi))
    }
}
