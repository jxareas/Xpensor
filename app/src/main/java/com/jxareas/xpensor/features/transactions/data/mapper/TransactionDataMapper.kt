package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDay
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails

fun Transaction.toTransactionEntity(accountId: Int, categoryId: Int): TransactionEntity =
    TransactionEntity(
        id = id,
        note = note,
        amount = amount,
        date = date,
        time = time,
        accountId = accountId,
        categoryId = categoryId,
    )

fun TransactionView.toTransactionWithDetails(): TransactionDetails =
    TransactionDetails(
        transaction = Transaction(
            id = transactionId,
            note = transactionNote,
            amount = transactionAmount,
            date = transactionDate,
            time = transactionTime,
        ),
        category = Category(
            categoryId,
            categoryName,
            categoryIcon,
            categoryIconColor,
        ),
        account = Account(accountId, accountName, accountAmount, accountColor),
    )

fun TransactionsByDay.toTransactionAmountPerDay(): TransactionByDay =
    TransactionByDay(
        date = transactionDate,
        amount = amountPerDay,
    )

fun TransactionDetails.toEntity(): TransactionEntity =
    TransactionEntity(
        id = transaction.id,
        note = transaction.note,
        amount = transaction.amount,
        date = transaction.date,
        time = transaction.time,
        accountId = account.id,
        categoryId = category.id,
        )
