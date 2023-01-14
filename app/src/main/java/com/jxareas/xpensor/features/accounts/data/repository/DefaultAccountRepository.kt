package com.jxareas.xpensor.features.accounts.data.repository

import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.data.local.source.AccountLocalDataSource
import com.jxareas.xpensor.features.accounts.data.mapper.toAccountEntity
import com.jxareas.xpensor.features.accounts.data.mapper.toAccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAccountRepository @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource,
) :
    AccountRepository {
    override fun getAccounts(): Flow<List<Account>> =
        accountLocalDataSource.getAll().map { listOfAccounts ->
            listOfAccounts.map(AccountEntity::toAccountWithDetails)
        }

    override suspend fun getAccountById(accountId: Int): Account? =
        accountLocalDataSource.getById(accountId)?.let(AccountEntity::toAccountWithDetails)

    override suspend fun insertAccount(account: Account) {
        val accountEntity = account.toAccountEntity()
        accountLocalDataSource.insert(accountEntity)
    }

    override suspend fun updateAccount(account: Account) {
        val accountEntity = account.toAccountEntity()
        accountLocalDataSource.update(accountEntity)
    }

    override suspend fun updateAccountAmount(accountId: Int, amount: Double) =
        accountLocalDataSource.updateAmount(accountId, amount)

    override suspend fun deleteAccount(account: Account) {
        val accountEntity = account.toAccountEntity()
        accountLocalDataSource.delete(accountEntity)
    }
}
