package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryWithAmountUiMapper @Inject constructor(
    private val categoryMapper: CategoryUiMapper,
) : Mapper<CategoryWithDetails, CategoryWithAmountUi> {

    override fun mapFromDomain(source: CategoryWithDetails): CategoryWithAmountUi =
        CategoryWithAmountUi(
            category = categoryMapper.mapFromDomain(source.category),
            amount = source.amount,
        )

    override fun mapToDomain(destination: CategoryWithAmountUi): CategoryWithDetails =
        CategoryWithDetails(
            category = categoryMapper.mapToDomain(destination.category),
            amount = destination.amount,
        )
}
