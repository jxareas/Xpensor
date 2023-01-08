package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.presentation.mapper.AccountUiMapper
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.features.transactions.presentation.mapper.CategoryWithAmountUiMapper
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
    private val accountUiMapper: AccountUiMapper,
    private val categoryUiMapper: CategoryWithAmountUiMapper,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryWithAmountUi>())
    val categories = _categories.asStateFlow()

    private val _events = MutableSharedFlow<CategorySelectionEvent>()
    val events = _events.asSharedFlow()

    private val _selectedAccount = MutableStateFlow<AccountUi?>(null)
    private val _selectedDateRange = MutableStateFlow<Pair<LocalDate?, LocalDate?>>(null to null)

    private var getCategoriesJob: Job? = null

    init {
        launchGetCategoriesJob()
    }

    private fun launchGetCategoriesJob() {
        getCategoriesJob?.cancel()
        val account = _selectedAccount.value?.let(accountUiMapper::mapToDomain)
        getCategoriesJob =
            getCategoriesUseCase(_selectedDateRange.value, account)
                .onEach { categories ->
                    _categories.value = categoryUiMapper.mapFromList(categories)
                }
                .launchIn(viewModelScope)
    }

    fun setDateRange(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchGetCategoriesJob()
    }

    fun setSelectedAccount(accountUi: AccountUi? = null) {
        _selectedAccount.value = accountUi
        launchGetCategoriesJob()
    }

    fun selectCategoryClick(
        accountUi: AccountUi,
        categoryWithAmountUi: CategoryWithAmountUi,
    ) = launchScoped {
        _events.emit(CategorySelectionEvent.SelectCategory(accountUi, categoryWithAmountUi))
    }
}
