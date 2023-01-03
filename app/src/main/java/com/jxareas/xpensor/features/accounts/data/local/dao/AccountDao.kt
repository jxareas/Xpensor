package com.jxareas.xpensor.features.accounts.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jxareas.xpensor.core.data.database.dao.BaseDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao : BaseDao<AccountEntity> {

    @Query("SELECT * FROM accounts")
    fun getAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: Int): AccountEntity?

    @Query("UPDATE accounts SET amount = :amount WHERE id = :id")
    suspend fun updateAccountAmount(id: Int, amount: Double)
}
