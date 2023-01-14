package com.jxareas.xpensor.features.transactions.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.data.mockTransactionDetails
import com.jxareas.sharedtest.data.mockTransactionsByDay
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.domain.usecase.DeleteTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsWithDayUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TransactionsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @RelaxedMockK
    private lateinit var getTransactionsWithDayUseCase: GetTransactionsWithDayUseCase

    @RelaxedMockK
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase


    private lateinit var viewModel: TransactionsViewModel

    @Before
    fun setup() {

        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)

        every {
            getTransactionsUseCase.invoke(null to null, null)
        } answers { flowOf(mockTransactionDetails) }

        coEvery {
            getTransactionsWithDayUseCase.invoke(mockTransactionDetails, null to null, null)
        } answers { mockTransactionsByDay }

        viewModel = TransactionsViewModel(
            getTransactionsUseCase,
            getTransactionsWithDayUseCase,
            deleteTransactionUseCase,
        )
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {

            val accountUi = mockk<AccountUi>()
            val transactionDetails = mockk<TransactionDetails>()

            // Date Selection Event
            viewModel.onSelectedDateClick()
            assertEquals(TransactionEvent.DateSelected, awaitItem())

            // Transaction Creation Event
            viewModel.onAddTransactionClick(accountUi)
            val openTheAddTransactionSheet = TransactionEvent
                .OpenTheAddTransactionSheet(accountUi)
            assertEquals(openTheAddTransactionSheet, awaitItem())

            // Transaction Deletion Event
            viewModel.onDeleteButtonClick(transactionDetails)
            val showDeleteTransactionDialog = TransactionEvent
                .ShowTheDeleteTransactionDialog(transactionDetails)
            assertEquals(showDeleteTransactionDialog, awaitItem())

            // Confirm Transaction Deletion
            viewModel.onDeleteTransactionConfirm(transactionDetails)
            val confirmTransactionDeletion = TransactionEvent
                .DeleteTransaction(transactionDetails)
            assertEquals(confirmTransactionDeletion, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }


}
