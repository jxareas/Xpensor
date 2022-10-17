package com.jxareas.xpensor.domain.repository

import com.jxareas.xpensor.data.local.views.DayInformationView
import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {

    fun getTransactionViews(from: LocalDate, to: LocalDate): Flow<List<TransactionView>>

    fun getTransactionViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionView>>

    fun getTransactionAmountsPerDay(from: LocalDate, to: LocalDate): Flow<List<DayInformationView>>

    fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<DayInformationView>>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(transactionId: Int)

}