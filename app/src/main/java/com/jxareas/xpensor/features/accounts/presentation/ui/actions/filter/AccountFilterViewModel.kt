package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.AccountUiMapper
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
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
class AccountFilterViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val accountUiMapper: AccountUiMapper,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<AccountUi>())
    val accounts = _accounts.asStateFlow()

    private val _events = MutableSharedFlow<AccountFilterEvent>()
    val events = _events.asSharedFlow()

    private var getAccountsJob: Job? = null

    init {
        getAccounts()
    }

    private fun getAccounts() {
        getAccountsJob?.cancel()
        getAccountsJob = getAccountsUseCase()
            .onEach { accounts ->
                _accounts.value = accountUiMapper.mapFromList(accounts)
            }
            .launchIn(viewModelScope)
    }

    fun onAccountSelected(account: AccountUi) = launchScoped {
        _events.emit(AccountFilterEvent.SelectAccount(account))
    }

    fun getTotalAccountsAmount(): Double =
        _accounts.value.sumOf(AccountUi::amount)
}
