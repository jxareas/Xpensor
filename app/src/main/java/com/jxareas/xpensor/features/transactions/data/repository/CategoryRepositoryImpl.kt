package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.data.mapper.asCategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao,
) : CategoryRepository {

    override fun getCategoryViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<CategoryWithDetails>> =
        dao.getCategoryViewsForAccount(from, to, id).mapList(CategoryView::asCategoryWithDetails)


    override fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryWithDetails>> =
        dao.getCategoryViews(from, to).mapList(CategoryView::asCategoryWithDetails)
}
