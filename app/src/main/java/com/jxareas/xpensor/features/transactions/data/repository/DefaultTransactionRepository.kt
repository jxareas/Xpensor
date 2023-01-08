package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.data.mapper.toDomain
import com.jxareas.xpensor.features.transactions.data.mapper.toEntity
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
            .mapEach(TransactionView::toDomain)

    override fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViewsForAccount(from, to, id)
            .mapEach(TransactionView::toDomain)

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDay(from, to)
            .mapEach(TransactionsByDateView::toDomain)

    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDayForAccount(from, to, transactionId)
            .mapEach(TransactionsByDateView::toDomain)

    override suspend fun insertTransaction(transaction: Transaction) {
        val transactionEntity = transaction.toEntity()
        dao.insertTransaction(transactionEntity)
    }

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)
}
