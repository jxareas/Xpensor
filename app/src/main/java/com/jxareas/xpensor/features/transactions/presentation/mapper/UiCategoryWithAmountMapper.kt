package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategory
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategoryWithAmount
import javax.inject.Inject

class UiCategoryWithAmountMapper @Inject constructor(
    private val categoryMapper: Mapper<Category, UiCategory>,
) : Mapper<CategoryWithDetails, UiCategoryWithAmount> {

    override fun mapFromDomain(source: CategoryWithDetails): UiCategoryWithAmount =
        UiCategoryWithAmount(
            category = categoryMapper.mapFromDomain(source.category),
            amount = source.amount,
        )

    override fun mapToDomain(destination: UiCategoryWithAmount): CategoryWithDetails =
        CategoryWithDetails(
            category = categoryMapper.mapToDomain(destination.category),
            amount = destination.amount,
        )
}