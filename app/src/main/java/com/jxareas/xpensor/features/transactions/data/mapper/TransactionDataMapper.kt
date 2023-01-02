package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDateView
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails

fun TransactionEntity.asTransaction() : Transaction =
    Transaction(
        id,
        note,
        amount,
        date,
        time,
        accountId,
        categoryId,
    )

fun Transaction.asTransactionEntity() : TransactionEntity =
    TransactionEntity(
        id,
        note,
        amount,
        date,
        time,
        accountId,
        categoryId
    )

fun TransactionsByDateView.asTransactionAmountPerDay(): TransactionAmountPerDay =
    TransactionAmountPerDay(transactionDate, amountPerDay)

fun TransactionView.asTransactionWithDetails() =
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
