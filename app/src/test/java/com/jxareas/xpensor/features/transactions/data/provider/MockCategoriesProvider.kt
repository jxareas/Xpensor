package com.jxareas.xpensor.features.transactions.data.provider

import com.jxareas.xpensor.features.transactions.presentation.mapper.asCategoryUi
import com.jxareas.xpensor.features.transactions.presentation.mapper.asCategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryUi
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

object MockCategoriesProvider {

    operator fun invoke() = List(20) { index ->
        CategoryWithAmountUi(
            categoryUi = CategoryUi(index, "category$index", index, "color$index"),
            amount = (0..100).random().toDouble()
        )
    }

    fun getOneCategoryUi() = this().first()
        .asCategoryWithDetails()
        .category
        .asCategoryUi()

}