package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

sealed class SelectCategoryUiEvent {
    data class SelectCategory(val account: AccountWithDetailsUi, val category: CategoryWithAmountUi) :
        SelectCategoryUiEvent()
}
