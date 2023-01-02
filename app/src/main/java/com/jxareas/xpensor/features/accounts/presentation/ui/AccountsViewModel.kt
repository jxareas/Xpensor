package com.jxareas.xpensor.features.accounts.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountUi
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
class AccountsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
) : ViewModel() {

    private val _accounts = MutableStateFlow(emptyList<AccountUi>())
    val accounts = _accounts.asStateFlow()

    private val _events = MutableSharedFlow<AccountUiEvent>()
    val events = _events.asSharedFlow()

    private var getAccountsJob: Job? = null

    init {
        launchGetAccountsJob()
    }

    fun onAddNewAccountButtonClick() = launchScoped {
        _events.emit(AccountUiEvent.NavigateToAddAccountScreen)
    }

    fun onAccountSelected(accountUi: AccountUi) = launchScoped {
        _events.emit(AccountUiEvent.OpenTheAccountBottomSheet(accountUi))
    }

    private fun launchGetAccountsJob() {
        getAccountsJob?.cancel()
        getAccountsJob = getAccountsUseCase()
            .mapList(AccountWithDetails::asAccountUi)
            .onEach(_accounts::value::set)
            .launchIn(viewModelScope)
    }
}
