package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.AddAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
) : ViewModel() {

    private val _events = MutableSharedFlow<AddAccountUiEvent>()
    val events = _events.asSharedFlow()

    fun addAccount(accountUi: AccountUi) = launchScoped {
        val accountWithDetails = accountUi.asAccountWithDetails()
        addAccountUseCase(accountWithDetails)
    }

    fun onApplyChangesButtonClick() = launchScoped {
        _events.emit(AddAccountUiEvent.CreateNewAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _events.emit(AddAccountUiEvent.SelectAccountColor(color))
    }
}
