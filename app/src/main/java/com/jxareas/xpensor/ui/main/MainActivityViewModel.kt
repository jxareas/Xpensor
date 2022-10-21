package com.jxareas.xpensor.ui.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.ui.main.event.MainActivityEvent
import com.jxareas.xpensor.utils.DateUtils
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.utils.launchScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<Account>())
    val accounts = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount = _selectedAccount.asStateFlow()

    private val _selectedDateRange = MutableStateFlow(DateUtils.defaultDateRange)
    val selectedDateRange = _selectedDateRange.asStateFlow()

    private val _events = MutableSharedFlow<MainActivityEvent>()
    val events = _events.asSharedFlow()

    private var getAccountsJob: Job? = null

    init {
        launchGetAccountsJob()
    }

    private fun launchGetAccountsJob() {
        getAccountsJob?.cancel()
        getAccountsJob = getAccountsUseCase()
            .onEach { accounts -> _accounts.value = accounts }
            .launchIn(viewModelScope)
    }

    fun getCurrency() =
        sharedPreferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY) ?: MAIN_CURRENCY

    fun onSettingsButtonClick() =
        viewModelScope.launch {
            _events.emit(MainActivityEvent.OpenTheSettingsScreen)
        }

    fun onSelectAccountButtonClick() {
        viewModelScope.launch {
            _events.emit(MainActivityEvent.OpenTheSelectAccountDialog)
        }
    }

    fun onUpdateCurrentAccount(account: Account?) {
        viewModelScope.launch {
            _selectedAccount.value = account
        }
    }

    fun onUpdateCurrentDateRange(begin: LocalDate?, end: LocalDate?) = launchScoped {
        _selectedDateRange.value = begin to end
        Log.d("DATE RANGE CHANGED", _selectedDateRange.value.toString())
    }


}