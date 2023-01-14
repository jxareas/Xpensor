package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.UpdateAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccount
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ViewModel() {

    private val _eventEmitter = Channel<EditAccountEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    fun updateAccount(accountUi: AccountUi) = launchScoped {
        val account = accountUi.toAccount()
        updateAccountUseCase.invoke(account)
    }

    fun onAccountEditionConfirmation() = launchScoped {
        _eventEmitter.send(EditAccountEvent.UpdateAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _eventEmitter.send(EditAccountEvent.UpdateCurrentColor(color))
    }
}
