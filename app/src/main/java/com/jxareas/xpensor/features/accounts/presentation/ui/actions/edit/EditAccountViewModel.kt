package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.getImageViewTint
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.features.accounts.domain.usecase.UpdateAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.AccountUiMapper
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val accountUiMapper: AccountUiMapper,
) : ViewModel() {

    private val _events = MutableSharedFlow<EditAccountUiEvent>()
    val events = _events.asSharedFlow()

    suspend fun onAccountUpdate(accountUi: AccountUi) =
        updateAccountUseCase(accountUiMapper.mapToDomain(accountUi))

    fun onApplyChanges() = launchScoped {
        _events.emit(EditAccountUiEvent.UpdateAccount)
    }

    fun onSelectColorButtonClick(image: ImageView) = launchScoped {
        val color = getImageViewTint(image)
        _events.emit(EditAccountUiEvent.UpdateCurrentColor(color))
    }
}
