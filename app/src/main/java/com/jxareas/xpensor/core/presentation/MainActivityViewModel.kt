package com.jxareas.xpensor.core.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.common.utils.PreferenceUtils.CURRENCY_PREFERENCE_KEY
import com.jxareas.xpensor.common.utils.PreferenceUtils.MAIN_CURRENCY
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem
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
class MainActivityViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val sharedPreferences: SharedPreferences,
    private val accountUiMapper: Mapper<AccountWithDetails, AccountListItem>,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<AccountListItem>())
    val accounts = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<AccountListItem?>(null)
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
            .onEach { accounts ->
                _accounts.value = accountUiMapper.toList(accounts)
            }
            .launchIn(viewModelScope)
    }

    fun getCurrency() =
        sharedPreferences.getString(CURRENCY_PREFERENCE_KEY, MAIN_CURRENCY) ?: MAIN_CURRENCY

    fun onSettingsButtonClick() = launchScoped {
        _events.emit(MainActivityEvent.OpenTheSettingsScreen)
    }

    fun onSelectAccountButtonClick() = launchScoped {
        _events.emit(MainActivityEvent.OpenTheSelectAccountDialog)
    }


    fun onUpdateSelectedAccount(account: AccountListItem?) = launchScoped {
        _selectedAccount.value = account
    }


    fun onUpdateCurrentDateRange(begin: LocalDate?, end: LocalDate?) = launchScoped {
        _selectedDateRange.value = begin to end
    }


}