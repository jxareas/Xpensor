package com.jxareas.sharedtest.data

import com.jxareas.xpensor.common.utils.DateUtils
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.presentation.mapper.asTransactionWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.TransactionDetailsUi
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalTime

val mockTransactionDetailsUi = List(NUMBER_OF_MOCKS) { index ->
    TransactionDetailsUi(
        id = index,
        note = "note$index",
        amount = index.toDouble(),
        date = LocalDate.parse("2020-12-08"),
        time = LocalTime.parse("14:14:20"),
        category = mockCategoryUi.first(),
        account = mockAccountUi.first(),
    )

}

val mockTransactionDetails =
    mockTransactionDetailsUi.map(TransactionDetailsUi::asTransactionWithDetails)

val mockTransactionsFlow =
    flowOf(mockTransactionDetails)

val mockTransactionsPerDay =
    List(NUMBER_OF_MOCKS) { index ->
        TransactionAmountPerDay(DateUtils.DEFAULT_LOCAL_DATE, index.toDouble())
    }
