package com.jxareas.sharedtest.data

import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.transactions.presentation.mapper.asCategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import kotlinx.coroutines.flow.flowOf


val mockCategoryAmountUi = List(NUMBER_OF_MOCKS) { index ->
    CategoryWithAmountUi(
        categoryUi = CategoryUi(index, "category$index", index, "color$index"),
        amount = index.toDouble(),
    )
}

val mockCategoryAmountFlow = flowOf(mockCategoryAmountUi)
    .mapList(CategoryWithAmountUi::asCategoryWithDetails)

val mockCategoryUi = mockCategoryAmountUi.map(CategoryWithAmountUi::categoryUi)
