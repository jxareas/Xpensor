package com.jxareas.xpensor.ui.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.GetAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
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

    private var getAccountsJob: Job? = null

    init {
        getAccounts()
    }

    private fun getAccounts() {
        getAccountsJob?.cancel()
        getAccountsJob = getAccountsUseCase()
            .onEach { accounts ->
                _accounts.value = accounts
            }
            .launchIn(viewModelScope)
    }

}