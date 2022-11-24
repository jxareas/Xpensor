package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.usecase.DeleteAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AccountActionsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val accountUiMapper: Mapper<AccountWithDetails, UiAccount>,
) : ViewModel() {

    private val _events = MutableSharedFlow<AccountActionsEvent>()
    val events = _events.asSharedFlow()

    suspend fun removeAccount(uiAccount: UiAccount) {
        val account = accountUiMapper.mapToDomain(uiAccount)
        deleteAccountUseCase(account)
    }

    fun onEditAccount(account: UiAccount) = launchScoped {
        _events.emit(AccountActionsEvent.NavigateToEditAccountsScreen(account))
    }

    fun onDeleteAccount(account: UiAccount) = launchScoped {
        _events.emit(AccountActionsEvent.ShowDeleteAccountDialog(account))
    }

    fun onDeleteAccountConfirmation() = launchScoped {
        _events.emit(AccountActionsEvent.DeleteAccount)
    }

}