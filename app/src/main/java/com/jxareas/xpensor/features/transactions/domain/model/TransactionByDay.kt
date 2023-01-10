package com.jxareas.xpensor.features.transactions.domain.model

import java.time.LocalDate

data class TransactionByDay(
    val date: LocalDate,
    val amount: Double,
)
