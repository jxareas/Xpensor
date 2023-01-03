package com.jxareas.xpensor.features.accounts.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jxareas.sharedtest.data.mockAccountEntities
import com.jxareas.sharedtest.rule.TestCoroutineRule
import com.jxareas.xpensor.core.data.database.XpensorDatabaseTest
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AccountDaoTest : XpensorDatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun insertAccountTest() = runTest {
        val mockAccount = mockAccountEntities.first()
        accountDao.insert(mockAccount)

        accountDao.getAccounts().test {
            val accounts = awaitItem()
            assertThat(accounts).contains(mockAccount)
            cancel()
        }
    }

    @Test
    fun deleteAccountTest() = runTest {
        val expectedSize = 4
        val mocks = mockAccountEntities.take(expectedSize + 1)
        val mockAccountToBeDeleted = mocks.first()
        val mockAccountNotToBeDeleted = mocks.last()

        mocks.forEach { accountDao.insert(it) }
        accountDao.delete(mockAccountToBeDeleted)

        accountDao.getAccounts().test {
            val accounts = awaitItem()
            assert(accounts.size == expectedSize)
            assertThat(accounts).contains(mockAccountNotToBeDeleted)
            assertThat(accounts).doesNotContain(mockAccountToBeDeleted)
            cancel()
        }
    }

    @Test
    fun updateAccountTest() = runTest {
        val initialAccount = AccountEntity(1, "Maze Bank Account", 120.25, "Red")
        accountDao.insert(initialAccount)
        val updatedAccount =
            initialAccount.copy(name = "Del Perro Account", amount = 97.50, color = "Blue")
        accountDao.update(updatedAccount)

        accountDao.getAccounts().test {
            val accounts = awaitItem()
            assert(accounts.size == 1)
            assertThat(accounts).contains(updatedAccount)
            assertThat(accounts).doesNotContain(initialAccount)
            cancel()
        }
    }


}
