package com.jxareas.xpensor.features.transactions.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jxareas.sharedtest.data.mockAccountEntityConstrained
import com.jxareas.sharedtest.data.mockCategoryEntityConstrained
import com.jxareas.sharedtest.data.mockTransactionEntities
import com.jxareas.sharedtest.rule.TestCoroutineRule
import com.jxareas.xpensor.core.data.database.XpensorDatabaseTest
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
    fun fillTransactionTableConstraints() {
        accountDao.insert(mockAccountEntityConstrained)
        categoryDao.insert(mockCategoryEntityConstrained)
    }

    @Test
    fun insertTransactionTest() = runTest {
        val mockTransaction = mockTransactionEntities.first()
        transactionDao.insert(mockTransaction)
        transactionDao.getTransactions().test {
            val transactions = awaitItem()
            assertThat(transactions).contains(mockTransaction)
            cancel()
        }
    }

    @Test
    fun updateTransactionTest() = runTest {
        val initialTransaction = mockTransactionEntities.first()
        transactionDao.insert(initialTransaction)
        val updatedTransaction = initialTransaction.copy(amount = 287.0, note = "Something Else")
        transactionDao.update(updatedTransaction)

        transactionDao.getTransactions().test {
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

        transactionDao.getTransactions().test {
            val transactions = awaitItem()
            assert(transactions.size == expectedSize)
            assertThat(transactions).contains(mockTransactionNotToBeDeleted)
            assertThat(transactions).doesNotContain(mockTransactionToBeDeleted)

            cancel()
        }
    }

}
