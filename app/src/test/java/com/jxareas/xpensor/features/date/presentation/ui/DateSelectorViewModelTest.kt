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
        viewModel.events.test {
            viewModel.onSelectDate()
            assertEquals(SelectDateEvent.CustomDate, awaitItem())

            viewModel.onSelectToday()
            assertEquals(SelectDateEvent.Today, awaitItem())

            viewModel.onSelectWeek()
            assertEquals(SelectDateEvent.Week, awaitItem())

            viewModel.onSelectYear()
            assertEquals(SelectDateEvent.Year, awaitItem())

            viewModel.onSelectAllTime()
            assertEquals(SelectDateEvent.AllTime, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
