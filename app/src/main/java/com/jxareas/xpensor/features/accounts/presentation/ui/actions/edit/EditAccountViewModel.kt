package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.UpdateAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<EditAccountUiEvent>()
    val event = _event.asSharedFlow()

    fun onAccountUpdate(accountUi: AccountWithDetailsUi) = launchScoped {
        val accountWithDetails = accountUi.asAccountWithDetails()
        updateAccountUseCase(accountWithDetails)
    }

    fun onApplyChanges() = launchScoped {
        _event.emit(EditAccountUiEvent.UpdateAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _event.emit(EditAccountUiEvent.UpdateCurrentColor(color))
    }
}

