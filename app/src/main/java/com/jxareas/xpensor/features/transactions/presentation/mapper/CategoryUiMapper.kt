package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

fun Category.toUi(): CategoryUi =
    CategoryUi(
        id = id,
        name = name,
        icon = icon,
        iconColor = iconColor,
    )

fun CategoryUi.toDomain(): Category =
    Category(
        id = id,
        name = name,
        icon = icon,
        iconColor = iconColor,
    )

fun CategoryWithAmountUi.toDomain(): CategoryWithDetails =
    CategoryWithDetails(
        category = category.toDomain(),
        amount = amount,
    )

fun CategoryWithDetails.toUi(): CategoryWithAmountUi =
    CategoryWithAmountUi(
        category = category.toUi(),
        amount = amount,
    )
