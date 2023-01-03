package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.data.mapper.asTransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.data.mapper.asTransactionEntity
import com.jxareas.xpensor.features.transactions.data.mapper.asTransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getTransactionDetails(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViews(from, to)
            .mapList(TransactionView::asTransactionWithDetails)

    override fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViewsForAccount(from, to, id)
            .mapList(TransactionView::asTransactionWithDetails)

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDay(from, to)
            .mapList(TransactionsByDateView::asTransactionAmountPerDay)

    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDayForAccount(from, to, transactionId)
            .mapList(TransactionsByDateView::asTransactionAmountPerDay)

    override suspend fun insertTransaction(transaction: Transaction) =
        dao.insert(transaction.asTransactionEntity())

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)
}
