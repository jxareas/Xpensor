package com.jxareas.xpensor.domain.repository

import com.jxareas.xpensor.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): Flow<List<Account>>

    suspend fun getAccountById(accountId: Int): Account?

    suspend fun insertAccount(account: Account)

    suspend fun updateAccount(account: Account)

    suspend fun updateAccountAmount(accountId: Int, amount: Double)

    suspend fun deleteAccount(account: Account)
}