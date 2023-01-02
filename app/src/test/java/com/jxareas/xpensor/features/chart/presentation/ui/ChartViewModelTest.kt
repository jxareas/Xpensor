package com.jxareas.xpensor.features.chart.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.xpensor.common.TestCoroutineRule
import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.transactions.data.provider.MockCategoriesProvider
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.features.transactions.presentation.mapper.asCategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ChartViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val categories = MockCategoriesProvider()

    @Mock
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase
    private lateinit var viewModel: ChartViewModel

    @Before
    fun setup() {
        getCategoriesUseCase = Mockito.mock(GetCategoriesUseCase::class.java)

        val mockCategoriesFlow = flowOf(categories)
            .mapList(CategoryWithAmountUi::asCategoryWithDetails)
        Mockito.`when`(getCategoriesUseCase(null to null, null))
            .thenReturn(mockCategoriesFlow)

        viewModel = ChartViewModel(getCategoriesUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            viewModel.onSelectedDate()
            assertEquals(ChartUiEvent.DateSelected, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}