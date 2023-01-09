package com.jxareas.xpensor.features.transactions.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDayView
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDao {

    @Query(
        """
    SELECT * FROM view_transactions
    WHERE transaction_date >= :from AND transaction_date <= :to
    ORDER BY transaction_date ASC, transaction_date ASC
    """,
    )
    fun getTransactionViews(from: LocalDate, to: LocalDate): Flow<List<TransactionView>>

    @Query(
        """
    SELECT * FROM view_transactions
    WHERE transaction_date >= :from AND transaction_date <= :to AND account_id = :id
    ORDER BY transaction_date ASC, transaction_time ASC
    """,
    )
    fun getTransactionViewsForAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionView>>

    @Query(
        """
    SELECT * FROM view_transactions_by_day
    WHERE date >= :from AND date <= :to
    GROUP BY date
    ORDER BY date ASC
    """,
    )
    fun getTransactionAmountsPerDay(from: LocalDate, to: LocalDate):
            Flow<List<TransactionsByDayView>>

    @Query(
        """
    SELECT date, SUM(amount) AS amount_per_day
    FROM transactions
    WHERE date >= :from AND date <= :to AND account_id = :id
    GROUP BY date
    ORDER BY date ASC
    """,
    )
    fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionsByDayView>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)
}
