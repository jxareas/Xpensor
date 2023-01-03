package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.data.mockAccountDetailsUi
import com.jxareas.sharedtest.data.mockCategoryAmountFlow
import com.jxareas.sharedtest.data.mockCategoryAmountUi
import com.jxareas.sharedtest.rule.TestCoroutineRule
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class SelectCategoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockAccounts = mockAccountDetailsUi
    private val mockCategories = mockCategoryAmountUi
    private val mockCategoriesFlow = mockCategoryAmountFlow

    @Mock
    lateinit var getCategoriesUseCase: GetCategoriesUseCase
    lateinit var viewModel: SelectCategoryViewModel

    @Before
    fun setup() {
        getCategoriesUseCase = Mockito.mock(GetCategoriesUseCase::class.java)
        Mockito.`when`(getCategoriesUseCase.invoke(null to null, null))
            .thenReturn(mockCategoriesFlow)

        viewModel = SelectCategoryViewModel(getCategoriesUseCase)
    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.event.test {
            val mockAccount = mockAccounts.first()
            val mockCategory = mockCategories.first()

            // Select Category
            viewModel.selectCategoryClick(mockAccount, mockCategory)
            val selectCategoryEvent = SelectCategoryUiEvent
                .SelectCategory(account = mockAccount, category = mockCategory)
            assertEquals(selectCategoryEvent, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}
