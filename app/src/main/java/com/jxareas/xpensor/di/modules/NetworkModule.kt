package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.data.api.constants.ApiConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val moshi = MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshi)
            .baseUrl(ApiConstants.CURRENCY_API_BASE_URL)
            .build()



}