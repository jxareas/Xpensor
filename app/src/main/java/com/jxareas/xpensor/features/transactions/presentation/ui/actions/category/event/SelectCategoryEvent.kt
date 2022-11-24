package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.event

import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategoryWithAmount

sealed class SelectCategoryEvent {
    data class SelectCategory(val account: UiAccount, val category: UiCategoryWithAmount) :
        SelectCategoryEvent()
}