package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategory

object UiCategoryMapper : Mapper<Category, UiCategory> {
    override fun mapFromDomain(source: Category): UiCategory =
        UiCategory(
            id = source.id,
            name = source.name,
            icon = source.icon,
            iconColor = source.iconColor,
        )

    override fun mapToDomain(destination: UiCategory): Category =
        Category(
            id = destination.id,
            name = destination.name,
            icon = destination.icon,
            iconColor = destination.iconColor,
        )

}