package com.jxareas.xpensor.ui.transactions.actions.category


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.ui.transactions.actions.category.event.SelectCategoryEvent
import com.jxareas.xpensor.utils.extensions.launchScoped
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

    private val _categories = MutableStateFlow(emptyList<CategoryView>())
    val categories = _categories.asStateFlow()


    private val _events = MutableSharedFlow<SelectCategoryEvent>()
    val events = _events.asSharedFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    private val _selectedDateRange = MutableStateFlow<Pair<LocalDate?, LocalDate?>>(null to null)

    private var getCategoriesJob: Job? = null

    init {
        launchGetCategoriesJob()
    }

    private fun launchGetCategoriesJob() {
        getCategoriesJob?.cancel()
        getCategoriesJob =
            getCategoriesUseCase(_selectedDateRange.value, _selectedAccount.value)
                .onEach { categories -> _categories.value = categories }
                .launchIn(viewModelScope)
    }

    fun setDateRange(from: LocalDate? = null, to: LocalDate? = null) {
        _selectedDateRange.value = from to to
        launchGetCategoriesJob()
    }

    fun setSelectedAccount(account: Account? = null) {
        _selectedAccount.value = account
        launchGetCategoriesJob()
    }

    fun selectCategoryClick(account: Account, categoryView: CategoryView) = launchScoped {
        _events.emit(SelectCategoryEvent.SelectCategory(account, categoryView))
    }

}