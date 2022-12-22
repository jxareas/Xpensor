package com.jxareas.xpensor.features.transactions.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDao {

    @Query(
        """
    SELECT transactions.id, transactions.note, transactions.amount, transactions.date,
    transactions.time, categories.id AS category_id, categories.name AS category_name, accounts.id AS account_id, 
    accounts.name AS account_name, categories.icon, categories.icon_color    
    FROM transactions 
    JOIN accounts ON accounts.id = transactions.account_id 
    JOIN categories ON categories.id = transactions.category_id 
    WHERE date >= :from AND date <= :to ORDER BY date ASC, time ASC
    """
    )
    fun getTransactionViews(from: LocalDate, to: LocalDate): Flow<List<TransactionView>>

    @Query(
        """
    SELECT transactions.id, transactions.note, transactions.amount, transactions.date, transactions.time, 
    categories.id AS category_id, categories.name AS category_name, accounts.id AS account_id, 
    accounts.name AS account_name, categories.icon, categories.icon_color
    FROM transactions
    JOIN accounts ON accounts.id = transactions.account_id 
    JOIN categories ON categories.id = transactions.category_id
    WHERE date >= :from AND date <= :to AND account_id = :id ORDER BY date ASC, time ASC
    """
    )
    fun getTransactionViewsForAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionView>>

    @Query(
        """
    SELECT date, SUM(amount) AS amount_per_day 
    FROM transactions 
    WHERE date >= :from AND date <= :to 
    GROUP BY date 
    ORDER BY date ASC
    """
    )
    fun getTransactionAmountsPerDay(from: LocalDate, to: LocalDate):
        Flow<List<TransactionsByDateView>>

    @Query(
        """
    SELECT date, SUM(amount) AS amount_per_day  
    FROM transactions 
    WHERE date >= :from AND date <= :to AND account_id = :id
    GROUP BY date
    ORDER BY date ASC
    """
    )
    fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionsByDateView>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)
}
