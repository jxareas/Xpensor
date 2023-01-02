package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails

fun CategoryView.asCategoryWithDetails() : CategoryWithDetails =
    CategoryWithDetails(
            category = Category(
                id = id,
                name = name,
                icon = icon,
                iconColor = iconColor
            ),
            amount = amount,
        )
