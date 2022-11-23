package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView

object CategoryViewMapper : Mapper<CategoryView, CategoryWithDetails> {
    override fun mapTo(source: CategoryView): CategoryWithDetails =
        CategoryWithDetails(
            category = Category(id = source.id,
                name = source.name,
                icon = source.icon,
                iconColor = source.iconColor),
            amount = source.amount,
        )

    override fun mapFrom(destination: CategoryWithDetails): CategoryView =
        CategoryView(
            id = destination.category.id,
            name = destination.category.name,
            icon = destination.category.icon,
            iconColor = destination.category.iconColor,
            amount = destination.amount,
        )

}