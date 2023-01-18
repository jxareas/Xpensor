package com.jxareas.xpensor.features.accounts.data.local.source

import com.jxareas.xpensor.core.data.local.source.LocalDataSource
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

interface AccountLocalDataSource : LocalDataSource<AccountEntity> {

    fun getAll(): Flow<List<AccountEntity>>

    fun getTotalAccountsAmount(): Flow<Double>

    suspend fun getById(id: Int): AccountEntity?

    suspend fun updateAmount(accountId: Int, amount: Double)

}
