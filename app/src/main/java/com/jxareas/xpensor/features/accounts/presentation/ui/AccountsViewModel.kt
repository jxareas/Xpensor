package com.jxareas.xpensor.features.accounts.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.domain.usecase.GetTotalAccountsAmountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccountUi
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.accounts.presentation.model.TotalAccountsAmountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTotalAccountsAmountUseCase: GetTotalAccountsAmountUseCase,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<AccountUi>())
    val accounts = _accounts.asStateFlow()

    private val _totalAccountAmount = MutableStateFlow<TotalAccountsAmountUi?>(null)
    val totalAccountsAmount = _totalAccountAmount.asStateFlow()

    private val _eventEmitter = Channel<AccountUiEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    private var fetchAccountsJob: Job? = null
    private var fetchAccountsAmountJob: Job? = null

    init {
        launchFetchAccountsJob()
        launchFetchAccountAmountsJob()
    }

    fun onAddNewAccountButtonClick() = launchScoped {
        _eventEmitter.send(AccountUiEvent.NavigateToAddAccountScreen)
    }

    fun onSelectAccountClick(accountUi: AccountUi) = launchScoped {
        _eventEmitter.send(AccountUiEvent.OpenTheAccountBottomSheet(accountUi))
    }

    private fun launchFetchAccountAmountsJob() {
        fetchAccountsAmountJob?.cancel()
        getTotalAccountsAmountUseCase.invoke()
            .onEach {
                val preferredCurrency = userPreferences.getPreferredCurrencyName()
                _totalAccountAmount.value = TotalAccountsAmountUi(it, preferredCurrency)
            }
            .launchIn(viewModelScope)

    }

    private fun launchFetchAccountsJob() {
        fetchAccountsJob?.cancel()
        fetchAccountsJob = getAccountsUseCase.invoke()
            .mapEach(Account::toAccountUi)
            .onEach(_accounts::value::set)
            .launchIn(viewModelScope)
    }
}
