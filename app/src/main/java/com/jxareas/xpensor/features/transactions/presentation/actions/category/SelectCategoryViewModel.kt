package com.jxareas.xpensor.features.transactions.presentation.actions.category


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.features.transactions.presentation.actions.category.event.SelectCategoryEvent
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
    private val accountUiMapper: Mapper<AccountWithDetails, AccountListItem>,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryWithDetails>())
    val categories = _categories.asStateFlow()


    private val _events = MutableSharedFlow<SelectCategoryEvent>()
    val events = _events.asSharedFlow()

    private val _selectedAccount = MutableStateFlow<AccountListItem?>(null)
    private val _selectedDateRange = MutableStateFlow<Pair<LocalDate?, LocalDate?>>(null to null)

    private var getCategoriesJob: Job? = null

    init {
        launchGetCategoriesJob()
    }

    private fun launchGetCategoriesJob() {
        getCategoriesJob?.cancel()
        val account = _selectedAccount.value?.let(accountUiMapper::mapFrom)
        getCategoriesJob =
            getCategoriesUseCase(_selectedDateRange.value, account)
                .onEach { categories -> _categories.value = categories }
                .launchIn(viewModelScope)
    }

    fun setDateRange(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchGetCategoriesJob()
    }

    fun setSelectedAccount(accountListItem: AccountListItem? = null) {
        _selectedAccount.value = accountListItem
        launchGetCategoriesJob()
    }

    fun selectCategoryClick(account: AccountListItem, categoryView: CategoryWithDetails) =
        launchScoped {
            _events.emit(SelectCategoryEvent.SelectCategory(account, categoryView))
        }

}