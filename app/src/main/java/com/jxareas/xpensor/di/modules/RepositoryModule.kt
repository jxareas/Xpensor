package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.data.repository.AccountRepositoryImpl
import com.jxareas.xpensor.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideAccountRepository(repository: AccountRepositoryImpl): AccountRepository

}