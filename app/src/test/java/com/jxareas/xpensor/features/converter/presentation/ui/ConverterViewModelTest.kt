package com.jxareas.xpensor.features.converter.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.converter.domain.usecase.ConvertCurrencyUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ConverterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase

    private lateinit var viewModel: ConverterViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        viewModel = ConverterViewModel(convertCurrencyUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {

            // Currency Swap
            viewModel.onSwapCurrenciesClick()
            val currencySwap = CurrencyConversionEvent.Swap
            assertEquals(currencySwap, awaitItem())

            // Currency Conversion
            viewModel.onConvertButtonClick()
            val currencyConversion = CurrencyConversionEvent.Convert
            assertEquals(currencyConversion, awaitItem())

            cancelAndIgnoreRemainingEvents()

        }
    }


}
