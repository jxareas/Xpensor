package com.jxareas.xpensor.features.accounts.data.repository

import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.data.mapper.asAccountEntity
import com.jxareas.xpensor.features.accounts.data.mapper.asAccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao,
) : AccountRepository {

    override fun getAccounts(): Flow<List<AccountWithDetails>> =
        dao.getAccounts().mapList(AccountEntity::asAccountWithDetails)

    override suspend fun getAccountById(accountId: Int): AccountWithDetails? =
        dao.getAccountById(accountId)?.let(AccountEntity::asAccountWithDetails)

    override suspend fun insertAccount(account: AccountWithDetails) =
        dao.insert(account.asAccountEntity())

    override suspend fun updateAccount(account: AccountWithDetails) =
        dao.update(account.asAccountEntity())

    override suspend fun updateAccountAmount(accountId: Int, amount: Double) =
        dao.updateAccountAmount(accountId, amount)

    override suspend fun deleteAccount(account: AccountWithDetails) =
        dao.delete(account.asAccountEntity())

}
