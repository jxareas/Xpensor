package com.jxareas.xpensor.features.transactions.data.provider

import com.jxareas.xpensor.common.extensions.mapList
import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.features.accounts.data.provider.MockAccountsProvider
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.presentation.mapper.asTransactionWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.TransactionDetailsUi
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalTime

object MockTransactionProvider {

    private val mockCategoryUi = MockCategoriesProvider.getOneCategoryUi()
    private val mockAccountUi = MockAccountsProvider.mockAccountUi

    val mockTransactions = List(20) { index ->
        val transactionAmount = (0..100).random().toDouble()
        TransactionDetailsUi(
            id = index,
            note = "note$index",
            amount = transactionAmount,
            date = LocalDate.parse("2020-12-08"),
            time = LocalTime.parse("14:14:20"),
            category = mockCategoryUi,
            account = mockAccountUi,
        )
    }

    val mockTransactionAmountsPerDay = List(20) { index ->
        TransactionAmountPerDay(DateUtils.DEFAULT_LOCAL_DATE, index.toDouble())
    }

    val mockTransaction = mockTransactions.first()
    val mockTransactionDetails =
        mockTransactions.map(TransactionDetailsUi::asTransactionWithDetails)

    val mockTransactionsFlow =
        flowOf(mockTransactions).mapList(TransactionDetailsUi::asTransactionWithDetails)
}