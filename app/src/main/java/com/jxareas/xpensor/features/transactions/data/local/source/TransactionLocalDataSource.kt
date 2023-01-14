package com.jxareas.xpensor.features.transactions.data.local.source

import com.jxareas.xpensor.core.data.local.source.LocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionWithCategoryAndAccount
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDayView
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionLocalDataSource : LocalDataSource<TransactionEntity> {

    fun getTransactionWithDetailsBetween(from: LocalDate, to: LocalDate):
            Flow<List<TransactionWithCategoryAndAccount>>

    fun getTransactionsByDayBetween(from : LocalDate, to : LocalDate) : Flow<List<TransactionsByDayView>>

    fun getTransactionsByDayForAccountBetween(
        from: LocalDate,
        to: LocalDate,
        accountId: Int,
    ): Flow<List<TransactionsByDayView>>

    fun getTransactionWithDetailsForAccountBetween(
        from: LocalDate,
        to: LocalDate,
        accountId: Int,
    ): Flow<List<TransactionWithCategoryAndAccount>>

    suspend fun deleteTransactionById(transactionId: Int)
}
