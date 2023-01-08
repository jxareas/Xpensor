package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.UpdateAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toDomain
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ViewModel() {

    private val _events = MutableSharedFlow<EditAccountEvent>()
    val events = _events.asSharedFlow()

    fun onAccountUpdate(accountUi: AccountUi) = launchScoped {
        val account = accountUi.toDomain()
        updateAccountUseCase(account)
    }

    fun onApplyChanges() = launchScoped {
        _events.emit(EditAccountEvent.UpdateAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _events.emit(EditAccountEvent.UpdateCurrentColor(color))
    }
}
