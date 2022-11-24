package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.usecase.UpdateAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val accountUiMapper: Mapper<AccountWithDetails, UiAccount>,
) : ViewModel() {

    private val _events = MutableSharedFlow<EditAccountEvent>()
    val events = _events.asSharedFlow()

    suspend fun onAccountUpdate(uiAccount: UiAccount) =
        updateAccountUseCase(accountUiMapper.mapToDomain(uiAccount))

    fun onApplyChanges() = launchScoped {
        _events.emit(EditAccountEvent.UpdateAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _events.emit(EditAccountEvent.UpdateCurrentColor(color))
    }


}