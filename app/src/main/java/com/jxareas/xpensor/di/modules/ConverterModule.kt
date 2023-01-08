package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.features.converter.data.api.ConverterApi
import com.jxareas.xpensor.features.converter.data.repository.DefaultCurrencyRepository
import com.jxareas.xpensor.features.converter.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConverterModule {

    @Binds
    @Singleton
    fun bindConverterRepository(repository: DefaultCurrencyRepository): CurrencyRepository

    companion object {

        @Provides
        @Singleton
        fun provideConverterService(retrofit: Retrofit): ConverterApi =
            retrofit.create()
    }
}
