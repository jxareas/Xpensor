package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.xpensor.common.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.data.provider.MockAccountsProvider
import com.jxareas.xpensor.features.accounts.domain.usecase.DeleteAccountUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class AccountActionsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockAccounts = MockAccountsProvider.mockAccounts

    private lateinit var viewModel: AccountActionsViewModel

    @Before
    fun setup() {
        val deleteAccountUseCase = Mockito.mock(DeleteAccountUseCase::class.java)
        viewModel = AccountActionsViewModel(deleteAccountUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            val (firstAccount, secondAccount) = mockAccounts.take(2)

            // Edit Account Event
            viewModel.onEditAccount(firstAccount)
            val navigateToEditAccountScreen = AccountActionsUiEvent
                .NavigateToEditAccountsScreen(firstAccount)
            assertEquals(navigateToEditAccountScreen, awaitItem())

            // Delete Account
            viewModel.onDeleteAccount(secondAccount)
            val showDeleteAccountDialog = AccountActionsUiEvent
                .ShowDeleteAccountDialog(secondAccount)
            assertEquals(showDeleteAccountDialog, awaitItem())

            // Confirm Account Deletion
            viewModel.onDeleteAccountConfirmation()
            val confirmAccountDeletion = AccountActionsUiEvent
                .DeleteAccount
            assertEquals(confirmAccountDeletion, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}