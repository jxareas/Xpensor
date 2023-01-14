package com.jxareas.xpensor.features.accounts.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.data.mockAccounts
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.mapper.toAccountUi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AccountsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var getAccountsUseCase: GetAccountsUseCase

    private lateinit var viewModel: AccountsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)

        every { getAccountsUseCase.invoke() } answers { flowOf(mockAccounts) }
        viewModel = AccountsViewModel(getAccountsUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {
            val accountUi = getAccountsUseCase.invoke().first().first().toAccountUi()

            // Account Selection Event
            viewModel.onAccountSelected(accountUi)
            val openAccountBottomSheet = AccountEvent
                .OpenTheAccountBottomSheet(accountUi)
            assertEquals(openAccountBottomSheet, awaitItem())

            // Add New Account
            viewModel.onAddNewAccountButtonClick()
            val navigateToAddAccountScreenEvent = AccountEvent
                .NavigateToAddAccountScreen
            assertEquals(navigateToAddAccountScreenEvent, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
