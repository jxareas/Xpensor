package com.jxareas.xpensor.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jxareas.xpensor.data.local.database.XpensorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideDatabase(app: Application): XpensorDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            XpensorDatabase::class.java,
            XpensorDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .createFromAsset("database/xpensor.db").build()
    }
}