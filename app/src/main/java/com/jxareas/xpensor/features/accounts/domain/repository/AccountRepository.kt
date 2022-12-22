package com.jxareas.xpensor.features.accounts.domain.repository

import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): Flow<List<AccountWithDetails>>

    suspend fun getAccountById(accountId: Int): AccountWithDetails?

    suspend fun insertAccount(account: AccountWithDetails)

    suspend fun updateAccount(account: AccountWithDetails)

    suspend fun updateAccountAmount(accountId: Int, amount: Double)

    suspend fun deleteAccount(account: AccountWithDetails)
}
