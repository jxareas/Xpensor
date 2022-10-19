package com.jxareas.xpensor.ui.date

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.ui.date.event.DateSelectedEvent
import com.jxareas.xpensor.utils.launchScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class DateSelectorViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableSharedFlow<DateSelectedEvent>()
    val events = _events.asSharedFlow()

    fun onSelectDate() = launchScoped {
        _events.emit(DateSelectedEvent.CustomDate)
    }

    fun onSelectToday() = launchScoped {
        _events.emit(DateSelectedEvent.Today)
    }

    fun onSelectWeek() = launchScoped {
        _events.emit(DateSelectedEvent.Week)
    }

    fun onSelectMonth() = launchScoped {
        _events.emit(DateSelectedEvent.Month)
    }

    fun onSelectYear() = launchScoped {
        _events.emit(DateSelectedEvent.Year)
    }

    fun onSelectAllTime() = launchScoped {
        _events.emit(DateSelectedEvent.AllTime)
    }

}