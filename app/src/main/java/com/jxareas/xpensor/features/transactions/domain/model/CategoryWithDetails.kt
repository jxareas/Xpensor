package com.jxareas.xpensor.features.transactions.domain.model

import com.jxareas.xpensor.core.domain.model.Domain

data class CategoryWithDetails(
    val category: Category,
    val amount: Double,
) : Domain