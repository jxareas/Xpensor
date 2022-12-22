package com.jxareas.xpensor.features.transactions.domain.repository

import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategoryViewsFromAccount(from: LocalDate, to: LocalDate, id: Int):
        Flow<List<CategoryWithDetails>>

    fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryWithDetails>>
}
