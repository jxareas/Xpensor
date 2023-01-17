package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccountUi
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
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
class AccountFilterViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<AccountUi>())
    val accounts = _accounts.asStateFlow()

    private val _eventEmitter = Channel<AccountFilterUiEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    private var fetchAccountsJob: Job? = null

    init {
        launchFetchAccountsJob()
    }

    private fun launchFetchAccountsJob() {
        fetchAccountsJob?.cancel()
        fetchAccountsJob = getAccountsUseCase.invoke()
            .mapEach(Account::toAccountUi)
            .onEach(_accounts::value::set)
            .launchIn(viewModelScope)
    }

    fun onSelectAccountClick(account: AccountUi) = launchScoped {
        _eventEmitter.send(AccountFilterUiEvent.SelectAccount(account))
    }

    fun getTotalAccountsAmount(): Double =
        _accounts.value.sumOf(AccountUi::amount)
}
