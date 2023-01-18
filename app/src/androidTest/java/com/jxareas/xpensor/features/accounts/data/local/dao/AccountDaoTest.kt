package com.jxareas.xpensor.features.accounts.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jxareas.sharedtest.data.mockAccountEntities
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.core.data.local.database.XpensorDatabaseTest
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
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

        accountDao.getAll().test {
            val accounts = awaitItem()
            assertThat(accounts).contains(mockAccount)
            cancel()
        }
    }

    @Test
    fun deleteAccountTest() = runTest {
        val expectedSize = 5
        val accounts = mockAccountEntities.take(expectedSize + 1)
        val accountToBeDeleted = accounts.first()
        val accountNotToBeDeleted = accounts.last()

        accounts.forEach { accountDao.insert(it) }
        accountDao.delete(accountToBeDeleted)

        accountDao.getAll().test {
            val accountsInDatabase = awaitItem()
            assertEquals(accountsInDatabase.size, expectedSize)
            assertThat(accountsInDatabase).contains(accountNotToBeDeleted)
            assertThat(accountsInDatabase).doesNotContain(accountToBeDeleted)
            cancel()
        }
    }

    @Test
    fun updateAccountTest() = runTest {
        val initialAccount = AccountEntity(1, "account", 200.0, "color")
        accountDao.insert(initialAccount)
        val updatedAccount = initialAccount.copy(name = "finalAccount", color = "finalColor")
        accountDao.update(updatedAccount)

        accountDao.getAll().test {
            val accountsInDatabase = awaitItem()
            assert(accountsInDatabase.size == 1)
            assertThat(accountsInDatabase).contains(updatedAccount)
            assertThat(accountsInDatabase).doesNotContain(initialAccount)
            cancel()
        }
    }


}
