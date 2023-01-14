package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.domain.usecase.AddAccountUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddAccountViewModelTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var addAccountUseCase: AddAccountUseCase

    private lateinit var viewModel: AddAccountViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        viewModel = AddAccountViewModel(addAccountUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {

            // Account Creation Confirmation
            viewModel.onCreateAccountConfirmation()
            val createNewAccount = AddAccountEvent.CreateNewAccount
            assertEquals(createNewAccount, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
