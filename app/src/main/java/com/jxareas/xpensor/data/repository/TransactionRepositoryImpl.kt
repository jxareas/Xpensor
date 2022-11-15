package com.jxareas.xpensor.data.repository

import com.jxareas.xpensor.data.local.dao.TransactionDao
import com.jxareas.xpensor.data.local.model.TransactionEntity
import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Transaction
import com.jxareas.xpensor.domain.model.TransactionWithDetails
import com.jxareas.xpensor.domain.model.TransactionsByDate
import com.jxareas.xpensor.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao,
    private val transactionMapper: DomainMapper<TransactionEntity, Transaction>,
    private val transactionViewMapper: DomainMapper<TransactionView, TransactionWithDetails>,
    private val transactionsByDateMapper: DomainMapper<TransactionsByDateView, TransactionsByDate>,
) : TransactionRepository {

    override fun getTransactionViews(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViews(from, to)
            .map { transactionViews -> transactionViewMapper.toDomainList(transactionViews) }

    override fun getTransactionViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionWithDetails>> =
        dao.getTransactionViewsForAccount(from, to, id)
            .map { transactions -> transactionViewMapper.toDomainList(transactions) }

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionsByDate>> =
        dao.getTransactionAmountsPerDay(from, to)
            .map { transactions -> transactionsByDateMapper.toDomainList(transactions) }


    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<TransactionsByDate>> =
        dao.getTransactionAmountsPerDayForAccount(from, to, transactionId)
            .map { transactions -> transactionsByDateMapper.toDomainList(transactions) }

    override suspend fun insertTransaction(transaction: Transaction) =
        dao.insertTransaction(transactionMapper.fromDomain(transaction))

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)

}