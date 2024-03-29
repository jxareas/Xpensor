package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

sealed class CategorySelectionUiEvent {
    data class SelectCategory(val account: AccountUi, val category: CategoryWithAmountUi) :
        CategorySelectionUiEvent()
}
