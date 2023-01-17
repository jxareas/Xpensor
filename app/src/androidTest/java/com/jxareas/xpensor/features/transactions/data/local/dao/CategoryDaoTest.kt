package com.jxareas.xpensor.features.transactions.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jxareas.sharedtest.data.mockCategoryEntities
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.core.data.local.database.XpensorDatabaseTest
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class CategoryDaoTest : XpensorDatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun insertCategoryTest() = runTest {
        val categoryToBeInserted = mockCategoryEntities.first()
        categoryDao.insert(categoryToBeInserted)

        categoryDao.getAll().test {
            val categories = awaitItem()
            assert(categories.size == 1)
            assertThat(categories).contains(categoryToBeInserted)
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

        categoryDao.getAll().test {
            val categories = awaitItem()
            assert(categories.size == 1)
            assertThat(categories).contains(updatedCategory)
            assertThat(categories).doesNotContain(initialCategory)
            cancel()
        }

    }

    @Test
    fun deleteCategoryTest() = runTest {
        val expectedSize = 5
        val categories = mockCategoryEntities.take(expectedSize + 1)
        val categoryNotToBeDeleted = categories.first()
        val categoryToBeDeleted = categories.last()

        categories.forEach { categoryDao.insert(it) }
        categoryDao.delete(categoryToBeDeleted)

        categoryDao.getAll().test {
            val categoriesInDatabase = awaitItem()
            assert(categoriesInDatabase.size == expectedSize)
            assertThat(categoriesInDatabase).contains(categoryNotToBeDeleted)
            assertThat(categoriesInDatabase).doesNotContain(categoryToBeDeleted)
            cancel()
        }

    }
}
