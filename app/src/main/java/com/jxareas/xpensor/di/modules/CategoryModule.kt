package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.data.local.dao.CategoryDao
import com.jxareas.xpensor.data.local.database.XpensorDatabase
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.data.mapper.CategoryViewMapper
import com.jxareas.xpensor.data.repository.CategoryRepositoryImpl
import com.jxareas.xpensor.domain.mapper.DomainMapper
import com.jxareas.xpensor.domain.model.CategoryWithDetails
import com.jxareas.xpensor.domain.repository.CategoryRepository
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
    fun bindCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository

    companion object {

        @Provides
        @Singleton
        fun provideCategoryDao(database: XpensorDatabase): CategoryDao =
            database.categoryDao

        @Provides
        @Singleton
        fun provideCategoryMapper(): DomainMapper<CategoryView, CategoryWithDetails> =
            CategoryViewMapper
    }

}