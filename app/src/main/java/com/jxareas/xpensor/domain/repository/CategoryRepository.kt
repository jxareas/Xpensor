package com.jxareas.xpensor.domain.repository

import com.jxareas.xpensor.data.local.views.CategoryView
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CategoryRepository {

    fun getCategoryViewsFromAccount(from: LocalDate, to: LocalDate, id: Int): Flow<List<CategoryView>>

    fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryView>>

}