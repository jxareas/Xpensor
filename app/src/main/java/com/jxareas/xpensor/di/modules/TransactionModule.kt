package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.core.data.local.database.XpensorDatabase
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.source.TransactionLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.source.impl.TransactionRoomLocalDataSource
import com.jxareas.xpensor.features.transactions.data.repository.DefaultTransactionRepository
import com.jxareas.xpensor.features.transactions.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TransactionModule {

    @Binds
    @Singleton
    fun bindTransactionRepository(repository: DefaultTransactionRepository): TransactionRepository

    @Binds
    @Singleton
    fun bindTransactionLocalDataSource(roomDataSource: TransactionRoomLocalDataSource):
            TransactionLocalDataSource

    companion object {

        @Provides
        @Singleton
        fun provideTransactionDao(database: XpensorDatabase): TransactionDao =
            database.transactionDao
    }
}
