package com.jxareas.xpensor.domain.repository

import com.jxareas.xpensor.domain.model.Transaction
import com.jxareas.xpensor.domain.model.TransactionWithDetails
import com.jxareas.xpensor.domain.model.TransactionsByDate
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {

    fun getTransactionViews(from: LocalDate, to: LocalDate): Flow<List<TransactionWithDetails>>

    fun getTransactionViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionWithDetails>>

    fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionsByDate>>

    fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionsByDate>>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(transactionId: Int)

}