package com.jxareas.sharedtest.data

import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.presentation.mapper.asCategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import kotlinx.coroutines.flow.flowOf

const val CONSTRAINED_CATEGORY_ID = 291

val mockCategoryEntityConstrained =
    CategoryEntity(CONSTRAINED_CATEGORY_ID, "category", 22, "something")

val mockCategoryAmountUi = List(NUMBER_OF_MOCKS) { index ->
    CategoryWithAmountUi(
        categoryUi = CategoryUi(index, "category$index", index, "color$index"),
        amount = index.toDouble(),
    )
}

val mockCategoryEntities = List(NUMBER_OF_MOCKS) { index ->
    val newIndex = index + MOCK_DATA_INDEX_OFFSET
    CategoryEntity(newIndex, "category$newIndex", newIndex, "$newIndex")
}

val mockCategoryAmountFlow = flowOf(mockCategoryAmountUi)
    .mapList(CategoryWithAmountUi::asCategoryWithDetails)

val mockCategoryUi = mockCategoryAmountUi.map(CategoryWithAmountUi::categoryUi)
