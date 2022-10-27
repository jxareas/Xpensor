package com.jxareas.xpensor.ui.accounts.actions.edit

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.usecase.UpdateAccountUseCase
import com.jxareas.xpensor.ui.accounts.actions.edit.events.EditAccountEvent
import com.jxareas.xpensor.utils.getImageViewTint
import com.jxareas.xpensor.utils.launchScoped
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

    suspend fun onAccountUpdate(account: Account) =
        updateAccountUseCase(account)

    fun onApplyChanges() = launchScoped {
        _events.emit(EditAccountEvent.UpdateAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _events.emit(EditAccountEvent.UpdateCurrentColor(color))
    }


}