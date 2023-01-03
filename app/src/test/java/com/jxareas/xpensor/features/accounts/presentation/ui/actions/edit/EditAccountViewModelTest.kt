package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rule.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.domain.usecase.UpdateAccountUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class EditAccountViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private lateinit var viewModel: EditAccountViewModel

    @Before
    fun setup() {
        val updateAccountUseCase = Mockito.mock(UpdateAccountUseCase::class.java)
        viewModel = EditAccountViewModel(updateAccountUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            // Confirm Account Edition
            viewModel.onApplyChanges()
            assertEquals(EditAccountUiEvent.UpdateAccount, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }


}
