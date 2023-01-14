package com.jxareas.xpensor.features.accounts.data.local.source.impl

import com.jxareas.xpensor.core.data.local.source.RoomLocalDataSource
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.data.local.source.AccountLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRoomLocalDataSource @Inject constructor(
    private val accountDao: AccountDao,
) : RoomLocalDataSource<AccountEntity>(accountDao), AccountLocalDataSource {

    override suspend fun updateAmount(accountId: Int, amount: Double) =
        accountDao.updateAccountAmount(accountId, amount)

    override fun getAll(): Flow<List<AccountEntity>> =
        accountDao.getAll()

    override suspend fun getById(id: Int): AccountEntity? =
        accountDao.getById(id)

}
