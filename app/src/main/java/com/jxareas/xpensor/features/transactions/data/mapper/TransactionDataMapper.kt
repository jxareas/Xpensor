package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.accounts.data.mapper.toAccount
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionWithCategoryAndAccount
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionsByDayView
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails

fun TransactionEntity.toTransaction(): Transaction =
    Transaction(id, note, amount, date, time)

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

fun TransactionsByDayView.toTransactionAmountPerDay(): TransactionByDay =
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

fun TransactionView.toTransactionWithCategoryAndAccount(): TransactionWithCategoryAndAccount =
    TransactionWithCategoryAndAccount(
        transactionEntity = TransactionEntity(
            id = transactionId,
            note = transactionNote,
            amount = transactionAmount,
            date = transactionDate,
            time = transactionTime,
            accountId = accountId,
            categoryId = categoryId,
        ),
        categoryEntity = CategoryEntity(
            categoryId,
            categoryName,
            categoryIcon,
            categoryIconColor,
        ),
        accountEntity = AccountEntity(accountId, accountName, accountAmount, accountColor),
    )

fun TransactionWithCategoryAndAccount.toTransactionDetails(): TransactionDetails =
    TransactionDetails(
        transaction = transactionEntity.toTransaction(),
        category = categoryEntity.toCategory(),
        account = accountEntity.toAccount(),
    )
