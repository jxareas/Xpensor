package com.jxareas.xpensor.features.chart.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.data.mockCategoryDetails
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChartViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    private lateinit var viewModel: ChartViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        every {
            getCategoriesUseCase.invoke(null to null, null)
        } returns flowOf(mockCategoryDetails)
        viewModel = ChartViewModel(getCategoriesUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {

            // Date Selection Event
            viewModel.onDateSelectedClick()
            assertEquals(ChartEvent.DateSelected, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
