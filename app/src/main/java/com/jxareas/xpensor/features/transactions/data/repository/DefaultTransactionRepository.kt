package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionWithDetails
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionEntity
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
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
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViews(from, to)
            .mapEach(TransactionView::toTransactionWithDetails)

    override fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViewsForAccount(from, to, id)
            .mapEach(TransactionView::toTransactionWithDetails)

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDay(from, to)
            .mapEach(TransactionsByDateView::toTransactionAmountPerDay)

    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDayForAccount(from, to, transactionId)
            .mapEach(TransactionsByDateView::toTransactionAmountPerDay)

    override suspend fun insertTransaction(transaction: Transaction) {
        val transactionEntity = transaction.toTransactionEntity()
        dao.insertTransaction(transactionEntity)
    }

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)
}
