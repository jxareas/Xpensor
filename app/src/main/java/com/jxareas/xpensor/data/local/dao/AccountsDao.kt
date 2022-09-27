package com.jxareas.xpensor.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jxareas.xpensor.data.local.entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Query("SELECT * FROM accounts")
    fun getAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: Int): Account?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    @Update
    suspend fun updateAccount(account: Account)

    @Query("UPDATE accounts SET amount = :amount WHERE id = :id")
    suspend fun updateAccountAmount(id: Int, amount: Double)

    @Delete
    suspend fun deleteAccount(account: Account)
}