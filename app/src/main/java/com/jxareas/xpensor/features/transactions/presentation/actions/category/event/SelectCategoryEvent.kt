package com.jxareas.xpensor.features.transactions.presentation.actions.category.event

import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails

sealed class SelectCategoryEvent {
    data class SelectCategory(val account: AccountListItem, val category: CategoryWithDetails) :
        SelectCategoryEvent()
}