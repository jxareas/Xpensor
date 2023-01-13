package com.jxareas.sharedtest.data

import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails

val mockCategoryDetails = mockList { index ->
    CategoryWithDetails(
        category = Category(
            id = index,
            name = "name$index",
            icon = index,
            iconColor = "iconColor$index",
        ),
        amount = index.toDouble(),
    )
}

val mockCategories = mockCategoryDetails.map(CategoryWithDetails::category)
