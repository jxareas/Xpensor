package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.transactions.domain.usecase.AddTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.ValidateTransactionUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddTransactionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var addTransactionUseCase: AddTransactionUseCase

    @RelaxedMockK
    private lateinit var validateTransactionUseCase: ValidateTransactionUseCase

    private lateinit var viewModel: AddTransactionViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        viewModel = AddTransactionViewModel(addTransactionUseCase, validateTransactionUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {

            // Transaction Creation Event
            viewModel.onConfirmTransactionCreation()
            val createNewTransactionEvent = AddTransactionUiEvent.CreateNewTransaction
            assertEquals(createNewTransactionEvent, awaitItem())

        }
    }

}
