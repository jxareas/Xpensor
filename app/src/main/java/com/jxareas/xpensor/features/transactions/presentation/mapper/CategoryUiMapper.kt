package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

fun CategoryUi.asCategory(): Category =
    Category(
        id = id,
        name = name,
        icon = icon,
        iconColor = iconColor,
    )

fun Category.asCategoryUi() =
    CategoryUi(
        id = id,
        name = name,
        icon = icon,
        iconColor = iconColor,
    )

fun CategoryWithAmountUi.asCategoryWithDetails(): CategoryWithDetails =
    CategoryWithDetails(
        category = categoryUi.asCategory(),
        amount = amount,
    )

fun CategoryWithDetails.asCategoryWithAmount(): CategoryWithAmountUi =
    CategoryWithAmountUi(
        categoryUi = category.asCategoryUi(),
        amount = amount,
    )
