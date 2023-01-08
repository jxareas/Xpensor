package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.core.data.database.XpensorDatabase
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
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

    companion object {

        @Provides
        @Singleton
        fun provideCategoryDao(database: XpensorDatabase): CategoryDao =
            database.categoryDao
    }
}
