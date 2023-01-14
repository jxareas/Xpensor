package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.DeleteAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccount
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AccountActionsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : ViewModel() {

    private val _eventEmitter = Channel<AccountActionsEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    fun removeAccount(accountUi: AccountUi) = launchScoped {
        val account = accountUi.toAccount()
        deleteAccountUseCase.invoke(account)
    }

    fun onEditAccountClick(account: AccountUi) = launchScoped {
        _eventEmitter.send(AccountActionsEvent.NavigateToEditAccountsScreen(account))
    }

    fun onDeleteAccountClick(account: AccountUi) = launchScoped {
        _eventEmitter.send(AccountActionsEvent.ShowDeleteAccountDialog(account))
    }

    fun onConfirmAccountDeletionClick() = launchScoped {
        _eventEmitter.send(AccountActionsEvent.DeleteAccount)
    }
}
