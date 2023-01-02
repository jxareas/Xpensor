package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.AddAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<AddAccountUiEvent>()
    val event = _event.asSharedFlow()

    fun addAccount(accountUi: AccountWithDetailsUi) = launchScoped {
        val accountWithDetails = accountUi.asAccountWithDetails()
        addAccountUseCase(accountWithDetails)
    }

    fun onApplyChanges() = launchScoped {
        _event.emit(AddAccountUiEvent.CreateNewAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _event.emit(AddAccountUiEvent.SelectAccountColor(color))
    }
}
