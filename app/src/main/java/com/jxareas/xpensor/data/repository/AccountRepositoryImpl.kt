package com.jxareas.xpensor.data.repository

import com.jxareas.xpensor.data.local.dao.AccountDao
import com.jxareas.xpensor.data.local.model.AccountEntity
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao,
    private val mapper: DomainMapper<AccountEntity, Account>,
) :
    AccountRepository {
    override fun getAccounts(): Flow<List<Account>> =
        dao.getAccounts().map { listOfAccounts ->
            listOfAccounts.map(mapper::toDomain)
        }

    override suspend fun getAccountById(accountId: Int): Account? =
        dao.getAccountById(accountId)?.let(mapper::toDomain)

    override suspend fun insertAccount(account: Account) =
        dao.insertAccount(mapper.fromDomain(account))

    override suspend fun updateAccount(account: Account) =
        dao.updateAccount(mapper.fromDomain(account))

    override suspend fun updateAccountAmount(accountId: Int, amount: Double) =
        dao.updateAccountAmount(accountId, amount)

    override suspend fun deleteAccount(account: Account) =
        dao.deleteAccount(mapper.fromDomain(account))
}