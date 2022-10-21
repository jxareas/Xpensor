package com.jxareas.xpensor.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.ui.chart.events.ChartEvent
import com.jxareas.xpensor.utils.launchScoped
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

    private val _categories = MutableStateFlow(emptyList<CategoryView>())
    val categories = _categories.asStateFlow()

    private val _events = MutableSharedFlow<ChartEvent>()
    val events = _events.asSharedFlow()

    private var getCategoriesJob: Job? = null

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    private val _selectedDateRange = MutableStateFlow<Pair<LocalDate?, LocalDate?>>(null to null)

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

    fun onSelectedDateClick() = launchScoped {
        _events.emit(ChartEvent.DateSelected)
    }


}