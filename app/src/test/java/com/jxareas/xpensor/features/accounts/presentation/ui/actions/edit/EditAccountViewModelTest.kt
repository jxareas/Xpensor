package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.domain.usecase.UpdateAccountUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EditAccountViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var updateAccountUseCase: UpdateAccountUseCase

    private lateinit var viewModel: EditAccountViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        viewModel = EditAccountViewModel(updateAccountUseCase)
    }

    @Test
    fun testEventFlow() = runTest {

        viewModel.eventSource.test {
            // Account Edition Confirmation
            viewModel.onAccountEditionConfirmation()
            val accountEditionConfirmation = EditAccountEvent.UpdateAccount
            assertEquals(accountEditionConfirmation, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }

    }

}
