package com.jxareas.xpensor.features.transactions.domain.repository

import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {

    fun getTransactionDetails(from: LocalDate, to: LocalDate): Flow<List<TransactionDetails>>

    fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionDetails>>

    fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionByDay>>

    fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionByDay>>

    suspend fun insertTransaction(transaction: Transaction, accountId: Int, categoryId: Int)

    suspend fun insertTransaction(details : TransactionDetails)

    suspend fun deleteTransactionById(transactionId: Int)
}
