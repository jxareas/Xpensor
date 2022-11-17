package com.jxareas.xpensor.ui.accounts.actions.add

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.usecase.AddAccountUseCase
import com.jxareas.xpensor.ui.accounts.actions.add.events.AddAccountEvent
import com.jxareas.xpensor.utils.extensions.getImageViewTint
import com.jxareas.xpensor.utils.extensions.launchScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
) : ViewModel() {

    private val _events = MutableSharedFlow<AddAccountEvent>()
    val events = _events.asSharedFlow()

    suspend fun addAccount(account: AccountWithDetails) =
        addAccountUseCase(account)

    fun onApplyChangesButtonClick() = launchScoped {
        _events.emit(AddAccountEvent.CreateNewAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _events.emit(AddAccountEvent.SelectAccountColor(color))
    }


}