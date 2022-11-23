package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.core.domain.mapper.DomainMapper
import com.jxareas.xpensor.features.transactions.domain.repository.CategoryRepository
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao,
    private val categoryViewMapper: DomainMapper<CategoryView, CategoryWithDetails>,
) : CategoryRepository {

    override fun getCategoryViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<CategoryWithDetails>> =
        dao.getCategoryViewsForAccount(from, to, id)
            .map { categoryViews -> categoryViewMapper.toDomainList(categoryViews) }

    override fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryWithDetails>> =
        dao.getCategoryViews(from, to)
            .map { categoryViews -> categoryViewMapper.toDomainList(categoryViews) }


}