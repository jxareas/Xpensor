package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.event

import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

sealed class SelectCategoryEvent {
    data class SelectCategory(val account: AccountUi, val category: CategoryWithAmountUi) :
        SelectCategoryEvent()
}
