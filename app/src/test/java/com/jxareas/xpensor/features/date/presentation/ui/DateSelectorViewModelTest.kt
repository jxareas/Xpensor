package com.jxareas.xpensor.features.date.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rules.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DateSelectorViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val viewModel = DateSelectorViewModel()

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {
            viewModel.onSelectDate()
            assertEquals(SelectDateUiEvent.CustomDate, awaitItem())

            viewModel.onSelectToday()
            assertEquals(SelectDateUiEvent.Today, awaitItem())

            viewModel.onSelectWeek()
            assertEquals(SelectDateUiEvent.Week, awaitItem())

            viewModel.onSelectYear()
            assertEquals(SelectDateUiEvent.Year, awaitItem())

            viewModel.onSelectAllTime()
            assertEquals(SelectDateUiEvent.AllTime, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
