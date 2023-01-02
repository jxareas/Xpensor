package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.xpensor.common.TestCoroutineRule
import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.features.accounts.data.provider.MockAccountsProvider
import com.jxareas.xpensor.features.transactions.data.provider.MockCategoriesProvider
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.features.transactions.presentation.mapper.asCategoryWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

    private val mockAccounts = MockAccountsProvider.mockAccounts
    private val mockCategories = MockCategoriesProvider()

    @Mock
    lateinit var getCategoriesUseCase: GetCategoriesUseCase
    lateinit var viewModel: SelectCategoryViewModel

    @Before
    fun setup() {
        getCategoriesUseCase = Mockito.mock(GetCategoriesUseCase::class.java)

        val mockCategoriesFlow = flowOf(mockCategories)
            .mapList(CategoryWithAmountUi::asCategoryWithDetails)
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