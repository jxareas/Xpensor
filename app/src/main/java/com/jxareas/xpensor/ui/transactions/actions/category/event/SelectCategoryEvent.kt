package com.jxareas.xpensor.ui.transactions.actions.category.event

import com.jxareas.xpensor.domain.model.AccountWithDetails
import com.jxareas.xpensor.domain.model.CategoryWithDetails

sealed class SelectCategoryEvent {
    data class SelectCategory(val account: AccountWithDetails, val category: CategoryWithDetails) :
        SelectCategoryEvent()
}