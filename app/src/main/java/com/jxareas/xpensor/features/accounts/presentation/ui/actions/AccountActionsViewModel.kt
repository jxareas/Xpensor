package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.usecase.DeleteAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AccountActionsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val accountUiMapper: Mapper<AccountWithDetails, AccountListItem>,
) : ViewModel() {

    private val _events = MutableSharedFlow<AccountActionsEvent>()
    val events = _events.asSharedFlow()

    suspend fun removeAccount(accountListItem: AccountListItem) {
        val account = accountUiMapper.mapFrom(accountListItem)
        deleteAccountUseCase(account)
    }

    fun onEditAccount(account: AccountListItem) = launchScoped {
        _events.emit(AccountActionsEvent.NavigateToEditAccountsScreen(account))
    }

    fun onDeleteAccount(account: AccountListItem) = launchScoped {
        _events.emit(AccountActionsEvent.ShowDeleteAccountDialog(account))
    }

    fun onDeleteAccountConfirmation() = launchScoped {
        _events.emit(AccountActionsEvent.DeleteAccount)
    }

}