package com.jxareas.xpensor.features.accounts.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jxareas.xpensor.core.data.local.dao.RoomDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao : RoomDao<AccountEntity> {

    @Query("SELECT * FROM accounts")
    fun getAll(): Flow<List<AccountEntity>>

    @Query("SELECT SUM(amount) FROM accounts")
    fun getTotalAccountsAmount(): Flow<Double>

    @Query("SELECT * FROM accounts where id = :accountId")
    suspend fun getById(accountId: Int): AccountEntity?

    @Query("UPDATE accounts SET amount = :amount WHERE id = :id")
    suspend fun updateAccountAmount(id: Int, amount: Double)

}
