package com.jxareas.xpensor.features.transactions.domain.repository

import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getTransactionDetails(from: LocalDate, to: LocalDate): Flow<List<TransactionWithDetails>>

    fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionWithDetails>>

    fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionAmountPerDay>>

    fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionAmountPerDay>>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(transactionId: Int)
}
