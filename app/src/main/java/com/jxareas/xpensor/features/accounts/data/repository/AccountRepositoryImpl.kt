package com.jxareas.xpensor.features.accounts.data.repository

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao,
    private val mapper: Mapper<AccountWithDetails, AccountEntity>,
) :
    AccountRepository {
    override fun getAccounts(): Flow<List<AccountWithDetails>> =
        dao.getAccounts().map { listOfAccounts ->
            listOfAccounts.map(mapper::mapToDomain)
        }

    override suspend fun getAccountById(accountId: Int): AccountWithDetails? =
        dao.getAccountById(accountId)?.let(mapper::mapToDomain)

    override suspend fun insertAccount(account: AccountWithDetails) =
        dao.insertAccount(mapper.mapFromDomain(account))

    override suspend fun updateAccount(account: AccountWithDetails) =
        dao.updateAccount(mapper.mapFromDomain(account))

    override suspend fun updateAccountAmount(accountId: Int, amount: Double) =
        dao.updateAccountAmount(accountId, amount)

    override suspend fun deleteAccount(account: AccountWithDetails) =
        dao.deleteAccount(mapper.mapFromDomain(account))
}