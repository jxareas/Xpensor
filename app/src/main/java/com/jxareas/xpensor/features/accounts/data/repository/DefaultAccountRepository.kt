package com.jxareas.xpensor.features.accounts.data.repository

import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.data.mapper.toDomain
import com.jxareas.xpensor.features.accounts.data.mapper.toEntity
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAccountRepository @Inject constructor(
    private val dao: AccountDao,
) :
    AccountRepository {
    override fun getAccounts(): Flow<List<AccountWithDetails>> =
        dao.getAccounts().map { listOfAccounts ->
            listOfAccounts.map(AccountEntity::toDomain)
        }

    override suspend fun getAccountById(accountId: Int): AccountWithDetails? =
        dao.getAccountById(accountId)?.let(AccountEntity::toDomain)

    override suspend fun insertAccount(account: AccountWithDetails) {
        val accountEntity = account.toEntity()
        dao.insertAccount(accountEntity)
    }

    override suspend fun updateAccount(account: AccountWithDetails) {
        val accountEntity = account.toEntity()
        dao.updateAccount(accountEntity)
    }

    override suspend fun updateAccountAmount(accountId: Int, amount: Double) =
        dao.updateAccountAmount(accountId, amount)

    override suspend fun deleteAccount(account: AccountWithDetails) {
        val accountEntity = account.toEntity()
        dao.deleteAccount(accountEntity)
    }
}
