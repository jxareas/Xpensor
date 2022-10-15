package com.jxareas.xpensor.data.repository

import com.jxareas.xpensor.data.local.dao.TransactionDao
import com.jxareas.xpensor.data.local.model.TransactionEntity
import com.jxareas.xpensor.data.local.views.DayInfoView
import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Transaction
import com.jxareas.xpensor.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao,
    private val transactionMapper: DomainMapper<TransactionEntity, Transaction>,
) :
    TransactionRepository {
    override fun getTransactionViews(from: LocalDate, to: LocalDate): Flow<List<TransactionView>> =
        dao.getTransactionViews(from, to)

    override fun getTransactionViewsFromAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionView>> =
        dao.getTransactionViewsForAccount(from, to, id)

    override fun getTransactionAmountsPerDay(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<DayInfoView>> =
        dao.getTransactionAmountsPerDay(from, to)

    override fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        transactionId: Int,
    ): Flow<List<DayInfoView>> =
        dao.getTransactionAmountsPerDayForAccount(from, to, transactionId)

    override suspend fun insertTransaction(transaction: Transaction) =
        dao.insertTransaction(transactionMapper.fromDomain(transaction))

    override suspend fun deleteTransactionById(transactionId: Int) =
        dao.deleteTransactionById(transactionId)


}