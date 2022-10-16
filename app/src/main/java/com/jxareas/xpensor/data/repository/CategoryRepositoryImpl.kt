package com.jxareas.xpensor.data.repository

import com.jxareas.xpensor.data.local.dao.CategoryDao
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.domain.repository.CategoryRepository
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
    ): Flow<List<CategoryView>> =
        dao.getCategoryViewsForAccount(from, to, id)

    override fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryView>> =
        dao.getCategoryViews(from, to)


}