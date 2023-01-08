package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails

fun Transaction.toTransactionEntity(): TransactionEntity =
    TransactionEntity(
        id = id,
        note = note,
        amount = amount,
        date = date,
        time = time,
        accountId = accountId,
        categoryId = categoryId,
    )

fun TransactionView.toTransactionWithDetails() : TransactionWithDetails =
    TransactionWithDetails(
        id = id,
        note = note,
        amount = amount,
        date = date,
        time = time,
        category = Category(
            categoryId,
            categoryName,
            icon,
            iconColor
        ),
        account = Account(accountId, accountName)
    )

fun TransactionsByDateView.toTransactionAmountPerDay(): TransactionAmountPerDay =
    TransactionAmountPerDay(
        date = transactionDate,
        amount = amountPerDay,
    )
