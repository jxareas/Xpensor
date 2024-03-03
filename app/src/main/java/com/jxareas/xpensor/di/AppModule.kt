package com.jxareas.xpensor.di

import android.app.Application
import android.content.Context
import com.jxareas.xpensor.core.data.local.database.XpensorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context): XpensorDatabase =
        XpensorDatabase.provideWithContext(context)

}
