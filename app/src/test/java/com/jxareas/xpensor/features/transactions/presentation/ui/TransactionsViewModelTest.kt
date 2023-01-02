package com.jxareas.xpensor.features.transactions.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.xpensor.common.TestCoroutineRule
import com.jxareas.xpensor.common.utils.DateRange
import com.jxareas.xpensor.features.accounts.data.provider.MockAccountsProvider.mockAccountDetailsUi
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails
import com.jxareas.xpensor.features.transactions.data.provider.MockTransactionProvider
import com.jxareas.xpensor.features.transactions.domain.usecase.DeleteTransactionUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsUseCase
import com.jxareas.xpensor.features.transactions.domain.usecase.GetTransactionsWithDayUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class TransactionsViewModelTest {
    // Rules
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    // Mock Data
    private val mockTransaction = MockTransactionProvider.mockTransaction
    private val mockTransactionDetails = MockTransactionProvider.mockTransactionDetails
    private val mockTransactionsFlow = MockTransactionProvider.mockTransactionsFlow
    private val mockTransactionAmountsPerDay = MockTransactionProvider.mockTransactionAmountsPerDay

    // Mocks
    @Mock
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @Mock
    private lateinit var getTransactionsWithDayUseCase: GetTransactionsWithDayUseCase

    @Mock
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase

    private lateinit var viewModel: TransactionsViewModel

    @Before
    fun setup() = runBlocking {
        getTransactionsUseCase = Mockito.mock(GetTransactionsUseCase::class.java)
        getTransactionsWithDayUseCase = Mockito.mock(GetTransactionsWithDayUseCase::class.java)
        deleteTransactionUseCase =
            Mockito.mock(DeleteTransactionUseCase::class.java)

        val (emptyDateRange, emptyAccount) = Pair<DateRange, AccountWithDetails?>(
            null to null,
            null
        )

        Mockito.`when`(getTransactionsUseCase.invoke(emptyDateRange, emptyAccount))
            .thenReturn(mockTransactionsFlow)

        Mockito.`when`(
            getTransactionsWithDayUseCase.invoke(
                mockTransactionDetails,
                emptyDateRange,
                emptyAccount
            )
        ).thenReturn(mockTransactionAmountsPerDay)


        viewModel = TransactionsViewModel(
            getTransactionsUseCase, getTransactionsWithDayUseCase, deleteTransactionUseCase
        )
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            // Select Date
            viewModel.onSelectedDate()
            assertEquals(TransactionUiEvent.DateSelected, awaitItem())

            // Add Transaction
            viewModel.onAddNewTransaction(mockAccountDetailsUi)
            val openTheAddTransactionSheet = TransactionUiEvent
                .OpenTheAddTransactionSheet(mockAccountDetailsUi)
            assertEquals(openTheAddTransactionSheet, awaitItem())

            // Delete Transaction
            viewModel.onDeleteTransaction(mockTransaction)
            val showDeleteTransactionDialog = TransactionUiEvent
                .ShowDeleteTransactionDialog(mockTransaction)
            assertEquals(showDeleteTransactionDialog, awaitItem())

            // Confirm Transaction Deletion
            viewModel.onConfirmTransactionDeletion(mockTransaction)
            val confirmTransactionDeletion = TransactionUiEvent
                .DeleteTransaction(mockTransaction)
            assertEquals(confirmTransactionDeletion, awaitItem())

            cancelAndIgnoreRemainingEvents()

        }
    }

}