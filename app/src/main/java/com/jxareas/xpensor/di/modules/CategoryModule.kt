package com.jxareas.xpensor.di.modules

import com.jxareas.xpensor.core.data.database.XpensorDatabase
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.data.mapper.CategoryViewMapper
import com.jxareas.xpensor.features.transactions.data.repository.CategoryRepositoryImpl
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails
import com.jxareas.xpensor.features.transactions.domain.repository.CategoryRepository
import com.jxareas.xpensor.features.transactions.presentation.mapper.UiCategoryMapper
import com.jxareas.xpensor.features.transactions.presentation.mapper.UiCategoryWithAmountMapper
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategory
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategoryWithAmount
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

    @Binds
    @Singleton
    fun provideUiCategoryWithAmountMapper(mapper: UiCategoryWithAmountMapper):
            Mapper<CategoryWithDetails, UiCategoryWithAmount>

    companion object {

        @Provides
        @Singleton
        fun provideCategoryDao(database: XpensorDatabase): CategoryDao =
            database.categoryDao

        @Provides
        @Singleton
        fun provideCategoryViewMapper(): Mapper<CategoryWithDetails, CategoryView> =
            CategoryViewMapper

        @Provides
        @Singleton
        fun provideUiCategoryMapper(): Mapper<Category, UiCategory> =
            UiCategoryMapper
    }

}