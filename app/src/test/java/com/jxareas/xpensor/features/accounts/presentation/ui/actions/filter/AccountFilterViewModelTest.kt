package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.data.mockAccounts
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AccountFilterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var getAccountsUseCase: GetAccountsUseCase

    private lateinit var viewModel: AccountFilterViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        every { getAccountsUseCase.invoke() } answers { flowOf(mockAccounts) }
        viewModel = AccountFilterViewModel(getAccountsUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {
            val accountUi = mockk<AccountUi>()

            // Account Selection
            viewModel.onSelectAccountClick(accountUi)
            val selectAccount = AccountFilterUiEvent.SelectAccount(accountUi)
            assertEquals(selectAccount, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }

    }


}
