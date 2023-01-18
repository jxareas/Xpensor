package com.jxareas.xpensor.features.transactions.data.local.source.impl

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.core.data.local.source.RoomLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.source.TransactionLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionWithCategoryAndAccount
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDayView
import com.jxareas.xpensor.features.transactions.data.mapper.toTransactionWithCategoryAndAccount
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class TransactionRoomLocalDataSource @Inject constructor(
    private val transactionDao: TransactionDao,
) : RoomLocalDataSource<TransactionEntity>(transactionDao), TransactionLocalDataSource {
    override fun getTransactionWithDetailsBetween(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionWithCategoryAndAccount>> =
        transactionDao.getTransactionViews(from, to)
            .mapEach(TransactionView::toTransactionWithCategoryAndAccount)

    override fun getTransactionsByDayBetween(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<TransactionsByDayView>> =
        transactionDao.getTransactionAmountsPerDay(from, to)

    override fun getTransactionsByDayForAccountBetween(
        from: LocalDate,
        to: LocalDate,
        accountId: Int,
    ): Flow<List<TransactionsByDayView>> =
        transactionDao.getTransactionAmountsPerDayForAccount(from, to, accountId)

    override fun getTransactionWithDetailsForAccountBetween(
        from: LocalDate,
        to: LocalDate,
        accountId: Int,
    ): Flow<List<TransactionWithCategoryAndAccount>> =
        transactionDao.getTransactionViewsForAccount(from, to, accountId)
            .mapEach(TransactionView::toTransactionWithCategoryAndAccount)

    override suspend fun deleteTransactionById(transactionId: Int) =
        transactionDao.deleteById(transactionId)

}
