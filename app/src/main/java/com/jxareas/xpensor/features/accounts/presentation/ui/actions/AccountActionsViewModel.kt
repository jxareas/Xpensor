package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.DeleteAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AccountActionsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : ViewModel() {

    private val _events = MutableSharedFlow<AccountActionsUiEvent>()
    val events = _events.asSharedFlow()

    fun removeAccount(accountUi: AccountUi) = launchScoped {
        val accountWithDetails = accountUi.asAccountWithDetails()
        deleteAccountUseCase(accountWithDetails)
    }

    fun onEditAccount(account: AccountUi) = launchScoped {
        _events.emit(AccountActionsUiEvent.NavigateToEditAccountsScreen(account))
    }

    fun onDeleteAccount(account: AccountUi) = launchScoped {
        _events.emit(AccountActionsUiEvent.ShowDeleteAccountDialog(account))
    }

    fun onDeleteAccountConfirmation() = launchScoped {
        _events.emit(AccountActionsUiEvent.DeleteAccount)
    }
}
