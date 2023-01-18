package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

fun Category.toCategoryUi(): CategoryUi =
    CategoryUi(
        id = id,
        name = name,
        icon = icon,
        iconColor = iconColor,
    )

fun Category.toCategoryEntity(): CategoryEntity =
    CategoryEntity(id, name, icon, iconColor)

fun CategoryUi.toCategory(): Category =
    Category(
        id = id,
        name = name,
        icon = icon,
        iconColor = iconColor,
    )

fun CategoryWithAmountUi.toCategoryWithDetails(): CategoryWithDetails =
    CategoryWithDetails(
        category = category.toCategory(),
        amount = amount,
    )

fun CategoryWithDetails.toCategoryWithAmountUi(): CategoryWithAmountUi =
    CategoryWithAmountUi(
        category = category.toCategoryUi(),
        amount = amount,
    )
