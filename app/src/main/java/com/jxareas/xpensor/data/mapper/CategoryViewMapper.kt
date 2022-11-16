package com.jxareas.xpensor.data.mapper

import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Category
import com.jxareas.xpensor.domain.model.CategoryWithDetails

object CategoryViewMapper : DomainMapper<CategoryView, CategoryWithDetails> {
    override fun toDomain(entity: CategoryView): CategoryWithDetails =
        CategoryWithDetails(
            category = Category(id = entity.id,
                name = entity.name,
                icon = entity.icon,
                iconColor = entity.iconColor),
            amount = entity.amount,
        )

    override fun fromDomain(domain: CategoryWithDetails): CategoryView =
        CategoryView(
            id = domain.category.id,
            name = domain.category.name,
            icon = domain.category.icon,
            iconColor = domain.category.iconColor,
            amount = domain.amount,
        )

}