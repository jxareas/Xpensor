package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.domain.usecase.DeleteAccountUseCase
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AccountActionsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var deleteAccountUseCase: DeleteAccountUseCase

    private lateinit var viewModel: AccountActionsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        viewModel = AccountActionsViewModel(deleteAccountUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.events.test {
            val accountUi = mockk<AccountUi>()

            // Account Edition Event
            viewModel.onEditAccountClick(accountUi)
            val navigateToEditAccountsScreen = AccountActionsEvent
                .NavigateToEditAccountsScreen(accountUi)
            assertEquals(navigateToEditAccountsScreen, awaitItem())

            // Account Deletion Event
            viewModel.onDeleteAccountClick(accountUi)
            val showDeleteAccountDialog = AccountActionsEvent
                .ShowDeleteAccountDialog(accountUi)
            assertEquals(showDeleteAccountDialog, awaitItem())

            // Account Deletion Confirmation Event
            viewModel.onConfirmAccountDeletionClick()
            val confirmAccountDeletion = AccountActionsEvent.DeleteAccount
            assertEquals(confirmAccountDeletion, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }


}
