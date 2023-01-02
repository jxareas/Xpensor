package com.jxareas.xpensor.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountUi
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.converter.domain.usecase.GetPreferredCurrencyNameUseCase
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
    private val getPreferredCurrencyNameUseCase: GetPreferredCurrencyNameUseCase,
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
        getAccountsJob = getAccountsUseCase()
            .mapList(AccountWithDetails::asAccountUi)
            .onEach(_accounts::value::set)
            .launchIn(viewModelScope)
    }

    fun getCurrencyName() = getPreferredCurrencyNameUseCase()

    fun onSettingsButtonClick() = launchScoped {
        _events.emit(MainActivityEvent.OpenTheSettingsScreen)
    }

    fun onSelectAccountButtonClick() = launchScoped {
        _events.emit(MainActivityEvent.OpenTheSelectAccountDialog)
    }

    fun onUpdateSelectedAccount(account: AccountUi?) = launchScoped {
        _selectedAccount.value = account
    }

    fun onUpdateCurrentDateRange(begin: LocalDate?, end: LocalDate?) = launchScoped {
        _selectedDateRange.value = begin to end
    }
}
