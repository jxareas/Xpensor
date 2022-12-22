package com.jxareas.xpensor.features.transactions.data.repository

import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.mapper.TransactionAmountPerDayMapper
import com.jxareas.xpensor.features.transactions.data.mapper.TransactionMapper
import com.jxareas.xpensor.features.transactions.data.mapper.TransactionViewMapper
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao,
    private val transactionMapper: TransactionMapper,
    private val transactionViewMapper: TransactionViewMapper,
    private val transactionAmountPerDayMapper: TransactionAmountPerDayMapper,
) : TransactionRepository {

    override fun getTransactionDetails(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViews(from, to)
            .map { transactionViews -> transactionViewMapper.mapToList(transactionViews) }

    override fun getTransactionDetailsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViewsForAccount(from, to, id)
            .map { transactions -> transactionViewMapper.mapToList(transactions) }

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDay(from, to)
            .map { transactions -> transactionAmountPerDayMapper.mapToList(transactions) }

    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionAmountPerDay>> =
        dao.getTransactionAmountsPerDayForAccount(from, to, transactionId)
            .map { transactions -> transactionAmountPerDayMapper.mapToList(transactions) }

    override suspend fun insertTransaction(transaction: Transaction) =
        dao.insertTransaction(transactionMapper.mapFromDomain(transaction))

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)
}
