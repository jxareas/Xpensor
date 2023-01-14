package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.core.data.local.database.XpensorDatabase
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.local.source.AccountLocalDataSource
import com.jxareas.xpensor.features.accounts.data.local.source.impl.AccountRoomLocalDataSource
import com.jxareas.xpensor.features.accounts.data.repository.DefaultAccountRepository
import com.jxareas.xpensor.features.accounts.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {

    @Binds
    @Singleton
    fun bindAccountRepository(repository: DefaultAccountRepository): AccountRepository

    @Binds
    @Singleton
    fun bindAccountLocalDataSource(roomDataSource: AccountRoomLocalDataSource):
            AccountLocalDataSource

    companion object {

        @Provides
        @Singleton
        fun provideAccountDao(database: XpensorDatabase): AccountDao =
            database.accountDao
    }
}
