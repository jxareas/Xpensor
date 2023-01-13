package com.jxareas.sharedtest.data

import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import java.time.LocalDate
import java.time.LocalTime


val mockTransactions: List<Transaction> = mockList { index ->
    Transaction(
        id = index,
        note = "note$index",
        amount = index.toDouble(),
        date = LocalDate.parse("2020-12-08"),
        time = LocalTime.parse("14:14:20"),
    )
}

val mockTransactionDetails: List<TransactionDetails> =
    mockTransactions.mapIndexed { index, currentTransaction ->
        val currentCategory = mockCategories[index]
        val currentAccount = mockAccounts[index]
        TransactionDetails(
            transaction = currentTransaction,
            category = currentCategory,
            account = currentAccount,
        )
    }


val mockTransactionsByDay = mockList { index ->
    TransactionByDay(
        date = LocalDate.parse("2020-12-08"),
        amount = index.toDouble(),
    )
}
