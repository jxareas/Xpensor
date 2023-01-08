package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails

fun CategoryView.toDomain(): CategoryWithDetails =
    CategoryWithDetails(
        category = Category(id, name, icon, iconColor),
        amount = amount,
    )
