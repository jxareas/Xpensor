package com.jxareas.xpensor.features.transactions.domain.repository

import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CategoryRepository {

    fun getCategoryViewsFromAccount(from: LocalDate, to: LocalDate, id: Int): Flow<List<CategoryWithDetails>>

    fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryWithDetails>>

}