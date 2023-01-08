package com.jxareas.xpensor.features.transactions.domain.model

import java.time.LocalDate

data class TransactionAmountPerDay(
    val transactionDate: LocalDate,
    val amountPerDay: Double,
)
