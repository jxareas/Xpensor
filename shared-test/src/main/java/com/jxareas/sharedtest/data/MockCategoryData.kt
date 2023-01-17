package com.jxareas.sharedtest.data

import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.mapper.toCategoryEntity

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

val mockCategories: List<Category> =
    mockCategoryDetails.map(CategoryWithDetails::category)

val mockCategoryEntities: List<CategoryEntity> =
    mockCategories.map(Category::toCategoryEntity)
