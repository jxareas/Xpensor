package com.jxareas.xpensor.features.transactions.data.local.source

import com.jxareas.xpensor.core.data.local.source.LocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionAmountByCategory
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CategoryLocalDataSource : LocalDataSource<CategoryEntity> {

    fun getTransactionAmountByCategoryBetween(from: LocalDate, to: LocalDate):
            Flow<List<TransactionAmountByCategory>>

    fun getTransactionAmountByCategoryForAccountBetween(
        from: LocalDate,
        to: LocalDate,
        accountId: Int,
    ): Flow<List<TransactionAmountByCategory>>

}
