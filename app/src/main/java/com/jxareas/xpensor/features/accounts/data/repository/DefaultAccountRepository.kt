package com.jxareas.xpensor.features.accounts.data.repository

import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.data.mapper.toAccountWithDetails
import com.jxareas.xpensor.features.accounts.data.mapper.toAccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAccountRepository @Inject constructor(
    private val dao: AccountDao,
) :
    AccountRepository {
    override fun getAccounts(): Flow<List<Account>> =
        dao.getAccounts().map { listOfAccounts ->
            listOfAccounts.map(AccountEntity::toAccountWithDetails)
        }

    override suspend fun getAccountById(accountId: Int): Account? =
        dao.getAccountById(accountId)?.let(AccountEntity::toAccountWithDetails)

    override suspend fun insertAccount(account: Account) {
        val accountEntity = account.toAccountEntity()
        dao.insertAccount(accountEntity)
    }

    override suspend fun updateAccount(account: Account) {
        val accountEntity = account.toAccountEntity()
        dao.updateAccount(accountEntity)
    }

    override suspend fun updateAccountAmount(accountId: Int, amount: Double) =
        dao.updateAccountAmount(accountId, amount)

    override suspend fun deleteAccount(account: Account) {
        val accountEntity = account.toAccountEntity()
        dao.deleteAccount(accountEntity)
    }
}
