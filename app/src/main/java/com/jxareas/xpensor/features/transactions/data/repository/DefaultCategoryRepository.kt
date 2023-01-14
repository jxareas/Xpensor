package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.transactions.data.local.source.CategoryLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionAmountByCategory
import com.jxareas.xpensor.features.transactions.data.mapper.toCategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {

    override fun getCategoryViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<CategoryWithDetails>> =
        categoryLocalDataSource.getTransactionAmountByCategoryForAccountBetween(from, to, id)
            .mapEach(TransactionAmountByCategory::toCategoryWithDetails)

    override fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryWithDetails>> =
        categoryLocalDataSource.getTransactionAmountByCategoryBetween(from, to)
            .mapEach(TransactionAmountByCategory::toCategoryWithDetails)

}
