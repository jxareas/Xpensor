package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rule.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.domain.usecase.AddAccountUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class AddAccountViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private lateinit var viewModel: AddAccountViewModel

    @Before
    fun setup() {
        val addAccountUseCase = Mockito.mock(AddAccountUseCase::class.java)
        viewModel = AddAccountViewModel(addAccountUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {

            // Confirm New Account Creation
            viewModel.onApplyChanges()
            assertEquals(AddAccountUiEvent.CreateNewAccount, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
