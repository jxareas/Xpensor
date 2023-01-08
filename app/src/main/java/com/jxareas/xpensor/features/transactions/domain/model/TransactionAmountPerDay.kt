package com.jxareas.xpensor.features.transactions.domain.model

import java.time.LocalDate

data class TransactionAmountPerDay(
    val date: LocalDate,
    val amount: Double,
)
