package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.features.transactions.data.local.source.TransactionLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionWithCategoryAndAccount
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDayView
import com.jxareas.xpensor.features.transactions.data.mapper.toEntity
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionDetails
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionEntity
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DefaultTransactionRepository @Inject constructor(
    private val dao: TransactionLocalDataSource,
) : TransactionRepository {

    override fun getTransactionDetails(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionDetails>> =
        dao.getTransactionWithDetailsBetween(from, to)
            .mapEach(TransactionWithCategoryAndAccount::toTransactionDetails)

    override fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionDetails>> =
        dao.getTransactionWithDetailsForAccountBetween(from, to, id)
            .mapEach(TransactionWithCategoryAndAccount::toTransactionDetails)

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionByDay>> =
        dao.getTransactionsByDayBetween(from, to)
            .mapEach(TransactionsByDayView::toTransactionAmountPerDay)

    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionByDay>> =
        dao.getTransactionsByDayForAccountBetween(from, to, transactionId)
            .mapEach(TransactionsByDayView::toTransactionAmountPerDay)

    override suspend fun insertTransaction(
        transaction: Transaction,
        accountId: Int,
        categoryId: Int,
    ) {
        val transactionEntity = transaction.toTransactionEntity(accountId, categoryId)
        dao.insert(transactionEntity)
    }

    override suspend fun insertTransaction(details: TransactionDetails) {
        val transactionEntity = details.toEntity()
        dao.insert(transactionEntity)
    }

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)
}
