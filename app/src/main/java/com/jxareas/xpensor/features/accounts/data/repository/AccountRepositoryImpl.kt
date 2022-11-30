package com.jxareas.xpensor.features.accounts.data.repository

import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.mapper.AccountEntityMapper
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao,
    private val accountEntityMapper: AccountEntityMapper,
) :
    AccountRepository {
    override fun getAccounts(): Flow<List<AccountWithDetails>> =
        dao.getAccounts().map { listOfAccounts ->
            listOfAccounts.map(accountEntityMapper::mapToDomain)
        }

    override suspend fun getAccountById(accountId: Int): AccountWithDetails? =
        dao.getAccountById(accountId)?.let(accountEntityMapper::mapToDomain)

    override suspend fun insertAccount(account: AccountWithDetails) =
        dao.insertAccount(accountEntityMapper.mapFromDomain(account))

    override suspend fun updateAccount(account: AccountWithDetails) =
        dao.updateAccount(accountEntityMapper.mapFromDomain(account))

    override suspend fun updateAccountAmount(accountId: Int, amount: Double) =
        dao.updateAccountAmount(accountId, amount)

    override suspend fun deleteAccount(account: AccountWithDetails) =
        dao.deleteAccount(accountEntityMapper.mapFromDomain(account))
}