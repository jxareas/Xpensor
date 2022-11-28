package com.jxareas.xpensor.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.ui.main.event.MainActivityEvent
import com.jxareas.xpensor.utils.DateUtils
import com.jxareas.xpensor.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_CURRENCY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
<<<<<<< Updated upstream:app/src/main/java/com/jxareas/xpensor/ui/main/MainActivityViewModel.kt
import kotlinx.coroutines.launch
=======
import kotlinx.coroutines.flow.receiveAsFlow
>>>>>>> Stashed changes:app/src/main/java/com/jxareas/xpensor/core/presentation/MainActivityViewModel.kt
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

    private val _events = Channel<MainActivityEvent>(Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

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

<<<<<<< Updated upstream:app/src/main/java/com/jxareas/xpensor/ui/main/MainActivityViewModel.kt
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
=======
    fun onSettingsButtonClick() = launchScoped {
        _events.send(MainActivityEvent.OpenTheSettingsScreen)
    }

    fun onSelectAccountButtonClick() = launchScoped {
        _events.send(MainActivityEvent.OpenTheSelectAccountDialog)
>>>>>>> Stashed changes:app/src/main/java/com/jxareas/xpensor/core/presentation/MainActivityViewModel.kt
    }

    fun onUpdateCurrentDateRange(begin: LocalDate?, end: LocalDate?) {
        viewModelScope.launch {
            _selectedDateRange.value = begin to end
        }
    }


}