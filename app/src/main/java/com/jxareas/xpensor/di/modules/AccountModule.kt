package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.core.data.database.XpensorDatabase
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.repository.AccountRepositoryImpl
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
    fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

    companion object {

        @Provides
        @Singleton
        fun provideAccountDao(database: XpensorDatabase): AccountDao =
            database.accountDao

    }

}