package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.data.mapper.toCategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val dao: CategoryDao,
) : CategoryRepository {

    override fun getCategoryViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<CategoryWithDetails>> =
        dao.getCategoryViewsForAccount(from, to, id)
            .mapEach(CategoryView::toCategoryWithDetails)

    override fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryWithDetails>> =
        dao.getCategoryViews(from, to)
            .mapEach(CategoryView::toCategoryWithDetails)

}
