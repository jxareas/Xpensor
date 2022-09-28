package com.jxareas.xpensor.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jxareas.xpensor.data.local.model.TransactionEntity
import com.jxareas.xpensor.data.local.views.DayInfoView
import com.jxareas.xpensor.data.local.views.TransactionView
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDao {

    @Query("""
    SELECT transactions.id, transactions.note, transactions.amount, transactions.date,
    transactions.time, categories.id AS categoryId, categories.name AS category_name, accounts.id AS accountId, 
    accounts.name AS account_name, categories.icon, categories.iconColor    
    FROM transactions 
    JOIN accounts ON accounts.id = transactions.accountId 
    JOIN categories ON categories.id = transactions.categoryId 
    WHERE date >= :from AND date <= :to ORDER BY date ASC, time ASC
    """)
    fun getTransactionViews(from: LocalDate, to: LocalDate): Flow<List<TransactionView>>

    @Query("""
    SELECT transactions.id, transactions.note, transactions.amount, transactions.date, transactions.time, 
    categories.id AS categoryId, categories.name AS category_name, accounts.id AS accountId, 
    accounts.name AS account_name, categories.icon, categories.iconColor
    FROM transactions
    JOIN accounts ON accounts.id = transactions.accountId 
    JOIN categories ON categories.id = transactions.categoryId
    WHERE date >= :from AND date <= :to AND accountId = :id ORDER BY date ASC, time ASC
    """)
    fun getTransactionViewsForAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<TransactionView>>

    @Query("""
    SELECT date, SUM(amount) AS amount_per_day 
    FROM transactions 
    WHERE date >= :from AND date <= :to 
    GROUP BY date 
    ORDER BY date ASC
    """)
    fun getTransactionAmountsPerDay(from: LocalDate, to: LocalDate): Flow<List<DayInfoView>>

    @Query("""
    SELECT date, SUM(amount) AS amount_per_day  
    FROM transactions 
    WHERE date >= :from AND date <= :to AND accountId = :id
    GROUP BY date
    ORDER BY date ASC
    """)
    fun getTransactionAmountsPerDayForAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<DayInfoView>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)
}