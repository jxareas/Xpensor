package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import javax.inject.Inject

class CategoryViewMapper @Inject constructor() : Mapper<CategoryWithDetails, CategoryView> {

    override fun mapFromDomain(source: CategoryWithDetails): CategoryView =
        CategoryView(
            id = source.category.id,
            name = source.category.name,
            icon = source.category.icon,
            iconColor = source.category.iconColor,
            amount = source.amount,
        )

    override fun mapToDomain(destination: CategoryView): CategoryWithDetails =
        CategoryWithDetails(
            category = Category(
                id = destination.id,
                name = destination.name,
                icon = destination.icon,
                iconColor = destination.iconColor
            ),
            amount = destination.amount,
        )
}
