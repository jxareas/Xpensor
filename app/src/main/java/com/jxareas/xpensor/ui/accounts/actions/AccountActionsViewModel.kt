package com.jxareas.xpensor.ui.accounts.actions

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.usecase.DeleteAccountUseCase
import com.jxareas.xpensor.ui.accounts.actions.events.AccountActionsEvent
import com.jxareas.xpensor.utils.extensions.launchScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AccountActionsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : ViewModel() {

    private val _events = MutableSharedFlow<AccountActionsEvent>()
    val events = _events.asSharedFlow()

    suspend fun removeAccount(account: AccountWithDetails) {
        deleteAccountUseCase(account)
    }

    fun onEditAccount(account: AccountWithDetails) = launchScoped {
        _events.emit(AccountActionsEvent.NavigateToEditAccountsScreen(account))
    }

    fun onDeleteAccount(account: AccountWithDetails) = launchScoped {
        _events.emit(AccountActionsEvent.ShowDeleteAccountDialog(account))
    }

    fun onDeleteAccountConfirmation() = launchScoped {
        _events.emit(AccountActionsEvent.DeleteAccount)
    }

}