package com.jxareas.xpensor.features.transactions.presentation.mapper

import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccount
import com.jxareas.xpensor.features.accounts.presentation.mapper.asAccountUi
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.features.transactions.presentation.model.TransactionDetailsUi

fun TransactionWithDetails.asTransactionUi() =
    TransactionDetailsUi(
        id = id,
        note = note,
        amount = amount,
        date = date,
        time = time,
        category = category.asCategoryUi(),
        account = account.asAccountUi()
    )

fun TransactionDetailsUi.asTransactionWithDetails() =
    TransactionWithDetails(
        id = id,
        note = note,
        amount = amount,
        date = date,
        time = time,
        category = category.asCategory(),
        account = account.asAccount(),
    )