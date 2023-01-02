package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
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
class SelectCategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryWithAmountUi>())
    val categories = _categories.asStateFlow()

    private val _event = MutableSharedFlow<SelectCategoryUiEvent>()
    val event = _event.asSharedFlow()

    private val _selectedAccount = MutableStateFlow<AccountWithDetailsUi?>(null)
    private val _selectedDateRange = MutableStateFlow<Pair<LocalDate?, LocalDate?>>(null to null)

    private var getCategoriesJob: Job? = null

    init {
        launchGetCategoriesJob()
    }

    private fun launchGetCategoriesJob() {
        getCategoriesJob?.cancel()
        val account = _selectedAccount.value?.let(AccountWithDetailsUi::asAccountWithDetails)
        getCategoriesJob =
            getCategoriesUseCase(_selectedDateRange.value, account)
                .mapList(CategoryWithDetails::asCategoryWithAmount)
                .onEach(_categories::value::set)
                .launchIn(viewModelScope)
    }

    fun setDateRange(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchGetCategoriesJob()
    }

    fun setSelectedAccount(accountUi: AccountWithDetailsUi? = null) {
        _selectedAccount.value = accountUi
        launchGetCategoriesJob()
    }

    fun selectCategoryClick(
        accountUi: AccountWithDetailsUi,
        categoryWithAmountUi: CategoryWithAmountUi,
    ) = launchScoped {
        _event.emit(SelectCategoryUiEvent.SelectCategory(accountUi, categoryWithAmountUi))
    }
}
