package com.jxareas.xpensor.features.accounts.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.xpensor.common.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.data.provider.MockAccountsProvider
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class AccountsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    // Mock Data
    private val mockAccounts = MockAccountsProvider.mockAccounts
    private val mockAccountsFlow = MockAccountsProvider.mockAccountsFlow

    @Mock
    private lateinit var getAccountsUseCase: GetAccountsUseCase
    private lateinit var viewModel: AccountsViewModel

    @Before
    fun setup() {
        getAccountsUseCase = Mockito.mock(GetAccountsUseCase::class.java)
        Mockito.`when`(getAccountsUseCase.invoke()).thenReturn(mockAccountsFlow)

        viewModel = AccountsViewModel(getAccountsUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            val firstAccount = mockAccounts.first()

            // Account Selection
            viewModel.onAccountSelected(firstAccount)
            val openAccountBottomSheet = AccountUiEvent
                .OpenAccountBottomSheet(firstAccount)
            assertEquals(openAccountBottomSheet, awaitItem())

            // Add New Account
            viewModel.onAddAccount()
            val navigateToAddAccountScreen = AccountUiEvent
                .NavigateToAddAccountScreen
            assertEquals(navigateToAddAccountScreen, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }


}