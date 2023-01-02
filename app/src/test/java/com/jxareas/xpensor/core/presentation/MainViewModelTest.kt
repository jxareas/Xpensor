package com.jxareas.xpensor.core.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.xpensor.common.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.data.provider.MockAccountsProvider
import com.jxareas.xpensor.features.accounts.domain.usecase.GetAccountsUseCase
import com.jxareas.xpensor.features.converter.data.MockCurrencyProvider
import com.jxareas.xpensor.features.converter.domain.usecase.GetPreferredCurrencyNameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    // Mock Data
    private val mockAccounts = MockAccountsProvider.mockAccounts
    private val mockAccountsFlow = MockAccountsProvider.mockAccountsFlow
    private val mockPreferredCurrencyName = MockCurrencyProvider.mockPreferredCurrencyName

    // Mocks
    @Mock
    private lateinit var getAccountsUseCase: GetAccountsUseCase

    @Mock
    private lateinit var getPreferredCurrencyNameUseCase: GetPreferredCurrencyNameUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        getAccountsUseCase = Mockito.mock(GetAccountsUseCase::class.java)
        getPreferredCurrencyNameUseCase = Mockito.mock(GetPreferredCurrencyNameUseCase::class.java)

        Mockito.`when`(getAccountsUseCase.invoke()).thenReturn(mockAccountsFlow)
        Mockito.`when`(getPreferredCurrencyNameUseCase.invoke())
            .thenReturn(mockPreferredCurrencyName)

        viewModel = MainViewModel(getAccountsUseCase, getPreferredCurrencyNameUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            viewModel.onOpenSettings()
            assertEquals(MainUiEvent.OpenTheSettingsScreen, awaitItem())

            viewModel.onSelectAccount()
            assertEquals(MainUiEvent.OpenTheSelectAccountDialog, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}