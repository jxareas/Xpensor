package com.jxareas.xpensor.features.transactions.domain.model

import com.jxareas.xpensor.features.accounts.domain.model.Account
import java.time.LocalDate
import java.time.LocalTime

data class TransactionWithDetails(
    val id: Int,
    val note: String,
    val amount: Double,
    val date: LocalDate,
    val time: LocalTime,
    val category: Category,
    val account: Account,
)
