package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.DeleteAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AccountActionsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<AccountActionsUiEvent>()
    val event = _event.asSharedFlow()

    fun removeAccount(accountUi: AccountWithDetailsUi) = launchScoped {
        val accountWithDetails = accountUi.asAccountWithDetails()
        deleteAccountUseCase(accountWithDetails)
    }

    fun onEditAccount(account: AccountWithDetailsUi) = launchScoped {
        _event.emit(AccountActionsUiEvent.NavigateToEditAccountsScreen(account))
    }

    fun onDeleteAccount(account: AccountWithDetailsUi) = launchScoped {
        _event.emit(AccountActionsUiEvent.ShowDeleteAccountDialog(account))
    }

    fun onDeleteAccountConfirmation() = launchScoped {
        _event.emit(AccountActionsUiEvent.DeleteAccount)
    }
}
