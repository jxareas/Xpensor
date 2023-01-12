package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDay
import com.jxareas.xpensor.features.transactions.data.mapper.toEntity
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionEntity
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DefaultTransactionRepository @Inject constructor(
    private val dao: TransactionDao,
) : TransactionRepository {

    override fun getTransactionDetails(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionDetails>> =
        dao.getTransactionViews(from, to)
            .mapEach(TransactionView::toTransactionWithDetails)

    override fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionDetails>> =
        dao.getTransactionViewsForAccount(from, to, id)
            .mapEach(TransactionView::toTransactionWithDetails)

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionByDay>> =
        dao.getTransactionAmountsPerDay(from, to)
            .mapEach(TransactionsByDay::toTransactionAmountPerDay)

    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionByDay>> =
        dao.getTransactionAmountsPerDayForAccount(from, to, transactionId)
            .mapEach(TransactionsByDay::toTransactionAmountPerDay)

    override suspend fun insertTransaction(
        transaction: Transaction,
        accountId: Int,
        categoryId: Int,
    ) {
        val transactionEntity = transaction.toTransactionEntity(accountId, categoryId)
        dao.insertTransaction(transactionEntity)
    }

    override suspend fun insertTransaction(details: TransactionDetails) {
        val transactionEntity = details.toEntity()
        dao.insertTransaction(transactionEntity)
    }

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)
}
