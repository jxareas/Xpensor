package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.core.data.database.XpensorDatabase
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.data.mapper.TransactionMapper
import com.jxareas.xpensor.features.transactions.data.mapper.TransactionViewMapper
import com.jxareas.xpensor.features.transactions.data.mapper.TransactionsByDateMapper
import com.jxareas.xpensor.features.transactions.data.repository.TransactionRepositoryImpl
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.features.transactions.domain.model.TransactionsByDate
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
    fun bindTransactionRepository(repository: TransactionRepositoryImpl): TransactionRepository

    companion object {

        @Provides
        @Singleton
        fun provideTransactionDao(database: XpensorDatabase): TransactionDao =
            database.transactionDao

        @Provides
        @Singleton
        fun provideTransactionMapper(): Mapper<Transaction, TransactionEntity> =
            TransactionMapper

        @Provides
        @Singleton
        fun provideTransactionViewMapper(): Mapper<TransactionWithDetails, TransactionView> =
            TransactionViewMapper

        @Provides
        @Singleton
        fun provideTransactionByDateMapper(): Mapper<TransactionsByDate, TransactionsByDateView> =
            TransactionsByDateMapper

    }

}