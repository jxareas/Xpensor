package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.core.data.local.database.XpensorDatabase
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.source.CategoryLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.source.impl.CategoryRoomLocalDataSource
import com.jxareas.xpensor.features.transactions.data.repository.DefaultCategoryRepository
import com.jxareas.xpensor.features.transactions.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CategoryModule {

    @Binds
    @Singleton
    fun bindCategoryRepository(repository: DefaultCategoryRepository): CategoryRepository

    @Binds
    @Singleton
    fun bindCategoryLocalDataSource(roomDataSource: CategoryRoomLocalDataSource):
            CategoryLocalDataSource

    companion object {

        @Provides
        @Singleton
        fun provideCategoryDao(database: XpensorDatabase): CategoryDao =
            database.categoryDao
    }
}
