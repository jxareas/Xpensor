package com.jxareas.xpensor.features.transactions.data.local.views

import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity

data class TransactionWithCategoryAndAccount(
    val transactionEntity: TransactionEntity,
    val categoryEntity: CategoryEntity,
    val accountEntity: AccountEntity,
)
