package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.data.local.model.AccountEntity
import com.jxareas.xpensor.data.mapper.AccountMapper
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.Account
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideAccountMapper(): DomainMapper<AccountEntity, Account> =
        AccountMapper
}