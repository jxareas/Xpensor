package com.jxareas.xpensor.features.transactions.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jxareas.sharedtest.data.mockTransactionDetails
import com.jxareas.sharedtest.data.mockTransactionEntities
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.core.data.local.database.XpensorDatabaseTest
import com.jxareas.xpensor.features.accounts.data.mapper.toAccountEntity
import com.jxareas.xpensor.features.transactions.presentation.mapper.toCategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TransactionDaoTest : XpensorDatabaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun fillTransactionTableConstraints() = runTest {
        mockTransactionDetails.forEach { details ->
            val categoryEntity = details.category.toCategoryEntity()
            val accountEntity = details.account.toAccountEntity()

            accountDao.insert(accountEntity)
            categoryDao.insert(categoryEntity)
        }
    }

    @Test
    fun insertTransactionTest() = runTest {
        val transactionToBeInserted = mockTransactionEntities.first()
        transactionDao.insert(transactionToBeInserted)

        transactionDao.getAll().test {
            val transactionsInDatabase = awaitItem()
            assertThat(transactionsInDatabase).contains(transactionToBeInserted)
            cancel()
        }
    }

    @Test
    fun updateTransactionTest() = runTest {
        val initialTransaction = mockTransactionEntities.first()
        transactionDao.insert(initialTransaction)
        val updatedTransaction = initialTransaction.copy(amount = 287.0, note = "Something Else")
        transactionDao.update(updatedTransaction)

        transactionDao.getAll().test {
            val transactions = awaitItem()
            assert(transactions.size == 1)
            assertThat(transactions).contains(updatedTransaction)
            assertThat(transactions).doesNotContain(initialTransaction)

            cancel()
        }
    }

    @Test
    fun deleteTransactionTest() = runTest {
        val expectedSize = 4
        val mocks = mockTransactionEntities.take(expectedSize + 1)
        val mockTransactionToBeDeleted = mocks.first()
        val mockTransactionNotToBeDeleted = mocks.last()

        mocks.forEach { transactionDao.insert(it) }
        transactionDao.delete(mockTransactionToBeDeleted)

        transactionDao.getAll().test {
            val transactions = awaitItem()
            assert(transactions.size == expectedSize)
            assertThat(transactions).contains(mockTransactionNotToBeDeleted)
            assertThat(transactions).doesNotContain(mockTransactionToBeDeleted)

            cancel()
        }
    }

}
