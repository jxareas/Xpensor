package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.AddAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccount
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
) : ViewModel() {

    private val _eventEmitter = Channel<AddAccountEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    fun createNewAccount(accountUi: AccountUi) = launchScoped {
        val account = accountUi.toAccount()
        addAccountUseCase.invoke(account)
    }

    fun onCreateAccountConfirmation() = launchScoped {
        _eventEmitter.send(AddAccountEvent.CreateNewAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _eventEmitter.send(AddAccountEvent.SelectAccountColor(color))
    }
}
