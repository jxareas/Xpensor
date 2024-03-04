package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.features.converter.data.remote.api.FreeCurrencyApi
import com.jxareas.xpensor.features.converter.data.remote.source.CurrencyRemoteDataSource
import com.jxareas.xpensor.features.converter.data.remote.source.impl.FreeCurrencyApiRemoteDataSource
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
interface CurrencyConverterModule {

    @Binds
    @Singleton
    fun bindConverterRepository(repository: DefaultCurrencyRepository): CurrencyRepository

    @Binds
    @Singleton
    fun bindCurrencyRemoteDataSource(remoteSource: FreeCurrencyApiRemoteDataSource):
            CurrencyRemoteDataSource

    companion object {

        @Provides
        @Singleton
        fun provideCurrencyConverterApi(retrofit: Retrofit): FreeCurrencyApi =
            retrofit.create()
    }
}
