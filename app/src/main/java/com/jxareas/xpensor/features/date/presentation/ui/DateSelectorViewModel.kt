package com.jxareas.xpensor.features.date.presentation.ui

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.DateUtils.DAY_IN_MS
import com.jxareas.xpensor.common.utils.DateUtils.getCurrentLocalDate
import com.jxareas.xpensor.common.utils.DateUtils.toLocalDate
import com.jxareas.xpensor.common.utils.DateUtils.toMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DateSelectorViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SelectDateUiEvent>()
    val event = _event.asSharedFlow()

    fun getDate(daysAgo: Int = 0): LocalDate =
        if (daysAgo != 0)
            ((getCurrentLocalDate()).toMilliseconds() + DAY_IN_MS - (daysAgo * DAY_IN_MS))
                .toLocalDate()
        else getCurrentLocalDate()

    fun onSelectDate() = launchScoped {
        _event.emit(SelectDateUiEvent.CustomDate)
    }

    fun onSelectToday() = launchScoped {
        _event.emit(SelectDateUiEvent.Today)
    }

    fun onSelectWeek() = launchScoped {
        _event.emit(SelectDateUiEvent.Week)
    }

    fun onSelectMonth() = launchScoped {
        _event.emit(SelectDateUiEvent.Month)
    }

    fun onSelectYear() = launchScoped {
        _event.emit(SelectDateUiEvent.Year)
    }

    fun onSelectAllTime() = launchScoped {
        _event.emit(SelectDateUiEvent.AllTime)
    }
}
