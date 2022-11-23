package com.jxareas.xpensor.features.transactions.presentation.actions.category.event

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails

sealed class SelectCategoryEvent {
    data class SelectCategory(val account: AccountWithDetails, val category: CategoryWithDetails) :
        SelectCategoryEvent()
}