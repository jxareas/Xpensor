package com.jxareas.xpensor.features.transactions.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jxareas.sharedtest.data.mockCategoryEntities
import com.jxareas.sharedtest.rule.TestCoroutineRule
import com.jxareas.xpensor.core.data.database.XpensorDatabaseTest
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CategoryDaoTest : XpensorDatabaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    // Mock Data
    private val mockCategories = mockCategoryEntities

    @Test
    fun insertCategoryTest() = runTest {
        val categoryToInsert = mockCategories.first()
        categoryDao.insert(categoryToInsert)
        categoryDao.getCategories().test {
            val categories = awaitItem()
            assertThat(categories).contains(categoryToInsert)
            cancel()
        }
    }

    @Test
    fun updateCategoryTest() = runTest {
        val initialCategory = CategoryEntity(1, "Cooking", 287, "someUglyBlue")
        categoryDao.insert(initialCategory)
        val updatedCategory =
            initialCategory.copy(name = "Gifts", iconColor = "aCoolColor")
        categoryDao.update(updatedCategory)

        categoryDao.getCategories().test {
            val categories = awaitItem()
            assert(categories.size == 1)
            assertThat(categories).contains(updatedCategory)
            assertThat(categories).doesNotContain(initialCategory)
            cancel()
        }

    }

    @Test
    fun deleteCategoryTest() = runTest {
        val expectedSize = 4
        val mocks = mockCategories.take(expectedSize + 1)
        val mockCategoryNotToBeDeleted = mocks.first()
        val mockCategoryToBeDeleted = mocks.last()

        mocks.forEach { categoryDao.insert(it) }
        categoryDao.delete(mockCategoryToBeDeleted)

        categoryDao.getCategories().test {
            val categories = awaitItem()
            assert(categories.size == expectedSize)
            assertThat(categories).contains(mockCategoryNotToBeDeleted)
            assertThat(categories).doesNotContain(mockCategoryToBeDeleted)
            cancel()
        }

    }



}
