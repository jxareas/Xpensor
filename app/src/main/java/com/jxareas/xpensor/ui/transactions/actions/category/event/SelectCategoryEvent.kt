package com.jxareas.xpensor.ui.transactions.actions.category.event

import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.domain.model.Account

sealed class SelectCategoryEvent {
    data class SelectCategory(val account: Account, val category: CategoryView) :
        SelectCategoryEvent()
}