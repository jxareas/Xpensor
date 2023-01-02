package com.jxareas.xpensor.features.converter.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.xpensor.common.TestCoroutineRule
import com.jxareas.xpensor.features.converter.domain.usecase.ConvertCurrencyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ConverterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase
    private lateinit var viewModel: ConverterViewModel


    @Before
    fun setup() {
        convertCurrencyUseCase = Mockito.mock(ConvertCurrencyUseCase::class.java)
        viewModel = ConverterViewModel(convertCurrencyUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            // Convert Currency
            viewModel.onConvertCurrency()
            assertEquals(ConvertCurrencyUiEvent.Convert, awaitItem())

            // Swap Currencies
            viewModel.onSwapCurrencies()
            assertEquals(ConvertCurrencyUiEvent.Swap, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }



}