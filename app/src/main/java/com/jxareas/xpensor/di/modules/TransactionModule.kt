package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.data.local.dao.TransactionDao
import com.jxareas.xpensor.data.local.database.XpensorDatabase
import com.jxareas.xpensor.data.local.model.TransactionEntity
import com.jxareas.xpensor.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.data.mapper.TransactionMapper
import com.jxareas.xpensor.data.mapper.TransactionViewMapper
import com.jxareas.xpensor.data.mapper.TransactionsByDateMapper
import com.jxareas.xpensor.data.repository.TransactionRepositoryImpl
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Transaction
import com.jxareas.xpensor.domain.model.TransactionWithDetails
import com.jxareas.xpensor.domain.model.TransactionsByDate
import com.jxareas.xpensor.domain.repository.TransactionRepository
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
    fun bindTransactionRepository(repository: TransactionRepositoryImpl): TransactionRepository

    companion object {

        @Provides
        @Singleton
        fun provideTransactionDao(database: XpensorDatabase): TransactionDao =
            database.transactionDao

        @Provides
        @Singleton
        fun provideTransactionMapper(): DomainMapper<TransactionEntity, Transaction> =
            TransactionMapper

        @Provides
        @Singleton
        fun provideTransactionViewMapper(): DomainMapper<TransactionView, TransactionWithDetails> =
            TransactionViewMapper

        @Provides
        @Singleton
        fun provideTransactionByDateMapper(): DomainMapper<TransactionsByDateView, TransactionsByDate> =
            TransactionsByDateMapper

    }

}