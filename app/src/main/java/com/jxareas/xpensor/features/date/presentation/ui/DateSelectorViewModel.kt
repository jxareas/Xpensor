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

    private val _events = MutableSharedFlow<DateSelectedUiEvent>()
    val events = _events.asSharedFlow()

    fun getDate(daysAgo: Int = 0): LocalDate =
        if (daysAgo != 0)
            ((getCurrentLocalDate()).toMilliseconds() + DAY_IN_MS - (daysAgo * DAY_IN_MS))
                .toLocalDate()
        else getCurrentLocalDate()

    fun onSelectDate() = launchScoped {
        _events.emit(DateSelectedUiEvent.CustomDate)
    }

    fun onSelectToday() = launchScoped {
        _events.emit(DateSelectedUiEvent.Today)
    }

    fun onSelectWeek() = launchScoped {
        _events.emit(DateSelectedUiEvent.Week)
    }

    fun onSelectMonth() = launchScoped {
        _events.emit(DateSelectedUiEvent.Month)
    }

    fun onSelectYear() = launchScoped {
        _events.emit(DateSelectedUiEvent.Year)
    }

    fun onSelectAllTime() = launchScoped {
        _events.emit(DateSelectedUiEvent.AllTime)
    }
}
