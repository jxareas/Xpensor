package com.jxareas.xpensor.core.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences.Companion.DEFAULT_CURRENCY
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccountUi
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.date.domain.model.DateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<AccountUi>())
    val accounts = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<AccountUi?>(null)
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
        getAccountsJob = getAccountsUseCase.invoke()
            .mapEach(Account::toAccountUi)
            .onEach { accounts ->
                _accounts.value = accounts
            }
            .launchIn(viewModelScope)
    }

    fun getCurrency() =
        userPreferences.getPreferredCurrencyName()

    fun onSettingsButtonClick() = launchScoped {
        _events.emit(MainActivityEvent.OpenTheSettingsScreen)
    }

    fun onSelectAccountButtonClick() = launchScoped {
        _events.emit(MainActivityEvent.OpenTheSelectAccountDialog)
    }

    fun onUpdateSelectedAccount(account: AccountUi?) = launchScoped {
        _selectedAccount.value = account
    }

    fun onUpdateCurrentDateRange(dateRange: DateRange) = launchScoped {
        val (begin, end) = dateRange
        _selectedDateRange.value = begin to end
    }
}
