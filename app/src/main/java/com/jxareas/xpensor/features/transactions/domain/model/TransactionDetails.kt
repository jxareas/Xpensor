package com.jxareas.xpensor.features.transactions.domain.model

import com.jxareas.xpensor.features.accounts.domain.model.Account

data class TransactionDetails(
    val transaction: Transaction,
    val category: Category,
    val account: Account,
)
