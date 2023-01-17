package com.jxareas.xpensor.features.date.presentation.ui

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.DateUtils.DAY_IN_MS
import com.jxareas.xpensor.common.utils.DateUtils.getCurrentLocalDate
import com.jxareas.xpensor.common.utils.DateUtils.toLocalDate
import com.jxareas.xpensor.common.utils.DateUtils.toMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DateSelectorViewModel @Inject constructor() : ViewModel() {

    private val _eventEmitter = Channel<SelectDateUiEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    fun getDate(daysAgo: Int = 0): LocalDate =
        if (daysAgo != 0)
            ((getCurrentLocalDate()).toMilliseconds() + DAY_IN_MS - (daysAgo * DAY_IN_MS))
                .toLocalDate()
        else getCurrentLocalDate()

    fun onSelectDate() = launchScoped {
        _eventEmitter.send(SelectDateUiEvent.CustomDate)
    }

    fun onSelectToday() = launchScoped {
        _eventEmitter.send(SelectDateUiEvent.Today)
    }

    fun onSelectWeek() = launchScoped {
        _eventEmitter.send(SelectDateUiEvent.Week)
    }

    fun onSelectMonth() = launchScoped {
        _eventEmitter.send(SelectDateUiEvent.Month)
    }

    fun onSelectYear() = launchScoped {
        _eventEmitter.send(SelectDateUiEvent.Year)
    }

    fun onSelectAllTime() = launchScoped {
        _eventEmitter.send(SelectDateUiEvent.AllTime)
    }
}
