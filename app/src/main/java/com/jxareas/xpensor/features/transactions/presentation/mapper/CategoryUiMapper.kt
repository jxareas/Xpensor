package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryUiMapper @Inject constructor() : Mapper<Category, CategoryUi> {
    override fun mapFromDomain(source: Category): CategoryUi =
        CategoryUi(
            id = source.id,
            name = source.name,
            icon = source.icon,
            iconColor = source.iconColor,
        )

    override fun mapToDomain(destination: CategoryUi): Category =
        Category(
            id = destination.id,
            name = destination.name,
            icon = destination.icon,
            iconColor = destination.iconColor,
        )

}