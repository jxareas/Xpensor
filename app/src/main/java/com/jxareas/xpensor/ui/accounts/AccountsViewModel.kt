package com.jxareas.xpensor.ui.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.ui.accounts.events.AccountEvent
import com.jxareas.xpensor.utils.launchScoped
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
class AccountsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<Account>())
    val accounts = _accounts.asStateFlow()

    private val _events = MutableSharedFlow<AccountEvent>()
    val events = _events.asSharedFlow()

    private var getAccountsJob: Job? = null

    init {
        launchGetAccountsJob()
    }

    fun onAddNewAccountButtonClick() = launchScoped {
        _events.emit(AccountEvent.NavigateToAddAccountScreen)
    }

    fun onAccountSelected(account: Account) = launchScoped {
        _events.emit(AccountEvent.OpenTheAccountBottomSheet(account))
    }

    private fun launchGetAccountsJob() {
        getAccountsJob?.cancel()
        getAccountsJob = getAccountsUseCase()
            .onEach { accounts ->
                _accounts.value = accounts
            }
            .launchIn(viewModelScope)
    }

}