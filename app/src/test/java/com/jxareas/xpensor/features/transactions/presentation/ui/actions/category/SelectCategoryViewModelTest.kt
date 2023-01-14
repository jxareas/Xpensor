package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jxareas.sharedtest.data.mockCategoryDetails
import com.jxareas.sharedtest.rules.TestCoroutineRule
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.transactions.domain.usecase.GetCategoriesUseCase
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import io.mockk.MockKAnnotations
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
class SelectCategoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    private lateinit var viewModel: SelectCategoryViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)

        every {
            getCategoriesUseCase.invoke(
                null to null,
                null,
            )
        } returns flowOf(mockCategoryDetails)

        viewModel = SelectCategoryViewModel(getCategoriesUseCase)

    }

    @Test
    fun testEventFlow() = runTest {
        viewModel.eventSource.test {

            val accountUi = mockk<AccountUi>()
            val categoryWithAmountUi = mockk<CategoryWithAmountUi>()

            // Category Selection Event
            viewModel.onSelectCategoryClick(accountUi, categoryWithAmountUi)
            val selectCategoryEvent =
                CategorySelectionEvent.SelectCategory(accountUi, categoryWithAmountUi)
            assertEquals(selectCategoryEvent, awaitItem())

        }
    }

}
