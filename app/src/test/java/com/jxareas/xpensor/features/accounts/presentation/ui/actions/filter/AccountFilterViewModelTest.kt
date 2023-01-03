package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.data.mockAccountDetailsFlow
import com.jxareas.sharedtest.data.mockAccountDetailsUi
import com.jxareas.sharedtest.rule.TestCoroutineRule
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
class AccountFilterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val mockAccounts = mockAccountDetailsUi
    private val mockAccountsFlow = mockAccountDetailsFlow

    @Mock
    private lateinit var getAccountsUseCase: GetAccountsUseCase
    private lateinit var viewModel: AccountFilterViewModel

    @Before
    fun setup() {
        getAccountsUseCase = Mockito.mock(GetAccountsUseCase::class.java)
        Mockito.`when`(getAccountsUseCase.invoke()).thenReturn(mockAccountsFlow)
        viewModel = AccountFilterViewModel(getAccountsUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.events.test {
            val mockAccount = mockAccounts.first()
            // Select Account Filter
            viewModel.onAccountSelected(mockAccount)
            val selectAccount = AccountFilterUiEvent.SelectAccount(mockAccount)
            assertEquals(selectAccount, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
