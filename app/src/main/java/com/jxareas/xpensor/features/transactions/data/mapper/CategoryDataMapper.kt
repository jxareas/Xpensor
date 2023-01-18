package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionAmountByCategory
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails


fun CategoryEntity.toCategory(): Category =
    Category(id, name, icon, iconColor)

fun TransactionAmountByCategory.toCategoryWithDetails(): CategoryWithDetails =
    CategoryWithDetails(
        category = categoryEntity.toCategory(),
        amount = transactionAmount,
    )

fun CategoryView.toCategoryWithTotalAmountView(): TransactionAmountByCategory =
    TransactionAmountByCategory(
        categoryEntity = CategoryEntity(id, name, icon, iconColor),
        transactionAmount = amount,
    )
